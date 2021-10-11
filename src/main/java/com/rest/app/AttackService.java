package com.rest.app;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.rest.app.model.Conf;
import com.rest.app.model.FirewallRule;
import com.rest.app.model.VirtualMachine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Singleton implementation for Attack service
 * This implementation is threadsafe and the configuration (VMs list & firewall rules) can be changed at run time.
 *
 * The implementation uses AtomicReference
 *   Impact: While we build the new data structure ({@link #reset} ) when configuration is updated we may answer result from old configuration
 */
@Slf4j
public class AttackService {
    private int maxCacheSize;
    private AtomicReference<DataStructure> dataStructure;

    /**
     * @param conf
     */
    public AttackService(Conf conf, int maxCacheSize) throws IllegalArgumentException{
        this.maxCacheSize = maxCacheSize;
        this.dataStructure = new AtomicReference(new DataStructure(conf, maxCacheSize));
    }

    /**
     * Reset the configuration VMs & Firewall Rules to the new configuration
     * @param conf
     */
    public void reset(Conf conf) throws IllegalArgumentException{
        DataStructure newDataStructure = new DataStructure(conf, maxCacheSize);
        this.dataStructure.set(newDataStructure);
    }

    /**
     * Virtual machine ids that can potentially attack VM 'id'
     * @param id
     * @return
     */
    public Set<String> attack(String id) {
        return dataStructure.get().attack(id);
    }

    /**
     * @return number of VMs
     */
    public int getVmsCount() {
        return dataStructure.get().virtualMachines.size();
    }

    /***************************************************************************************************************************/
    /*************************************************   Inner Classes  ********************************************************/
    /***************************************************************************************************************************/

    /**
     * Internal class to hold fast data structure
     */
    private static class DataStructure{

        private LoadingCache<String, Set<String>> cache;    //Should be thread safe

        //
        //Read only maps - no need to be thread safe
        private Map<String, VirtualMachine> virtualMachines = new HashMap();
        private Map<String, Set<VirtualMachine>> tagToVirtualMachine = new HashMap();
        private Map<String, FirewallRule> rules = new HashMap();
        private Map<String, Set<FirewallRule>> destTagToFirewallRule = new HashMap();

        /**
         * Constructor
         * @param conf
         */
        private DataStructure(Conf conf, int maxCacheSize) throws IllegalArgumentException{

            //
            //Build virtualMachines & tagToVirtualMachine
            for (VirtualMachine vm : conf.getVms()){
                if(virtualMachines.putIfAbsent(vm.getId(), vm) != null){
                    throw new IllegalArgumentException("Duplicate VM id: " + vm.getId());
                }

                vm.getTags().forEach(tag -> tagToVirtualMachine.computeIfAbsent(tag, s -> new HashSet()).add(vm));
            }

            //
            //Build rules & destTagToFirewallRule
            for (FirewallRule rule : conf.getRules()){
                if(rules.putIfAbsent(rule.getId(), rule) != null){
                    throw new IllegalArgumentException("Duplicate firewall rule id: " + rule.getId());
                }

                destTagToFirewallRule.computeIfAbsent(rule.getDestTag(), s -> new HashSet<>()).add(rule);
            }

            //
            //Cache loader
            CacheLoader<String, Set<String>> cacheLoader = new CacheLoader<String, Set<String>>() {

                @Override
                public Set<String> load(String id) throws Exception {
                    VirtualMachine vm = virtualMachines.get(id);
                    if(vm == null){
                        return Collections.EMPTY_SET;
                    }

                    Set<String> result = new HashSet();

                    for(String tag : vm.getTags()){
                        Set<FirewallRule> firewallRules = destTagToFirewallRule.get(tag);
                        if(firewallRules != null){
                            for(FirewallRule firewallRule : firewallRules){
                                Set<VirtualMachine> vms = tagToVirtualMachine.get(firewallRule.getSourceTag());
                                if(vms != null){
                                    for(VirtualMachine attackVm : vms){

                                        if(! attackVm.getId().equals(id)){//VM can't attack itself therefore we need filter.
                                            if(result.add(attackVm.getId())){
                                                log.debug("Attack - Source VM: {}, Dest VM: {}, Rule: {}", attackVm, vm, firewallRule);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    return result;
                }
            };

            cache = CacheBuilder.newBuilder().
                    maximumSize(maxCacheSize).
                    build(cacheLoader);
        }

        /**
         * @param id - VM id
         * @return VMs ids that can potentially attack VM 'id'
         */
        public Set<String> attack(String id){
            try{
                return cache.get(id);
            }
            catch (ExecutionException ex){
                throw new RuntimeException(ex); //Shouldn't happened
            }
        }
    }
}
