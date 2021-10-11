package com.rest.app.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.app.AttackService;
import com.rest.app.model.Conf;
import com.rest.app.model.VirtualMachine;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class AttackServiceTests {
    private static ObjectMapper mapper = new ObjectMapper();

    private static String INPUT_0 = "{\n" +
            "    \"vms\": [\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-a211de\",\n" +
            "            \"name\": \"jira_server\",\n" +
            "            \"tags\": [\n" +
            "                \"ci\",\n" +
            "                \"dev\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-c7bac01a07\",\n" +
            "            \"name\": \"bastion\",\n" +
            "            \"tags\": [\n" +
            "                \"ssh\",\n" +
            "                \"dev\"\n" +
            "            ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"fw_rules\": [\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-82af742\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"dev\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    private static String INPUT_1 = "{\n" +
            "    \"vms\": [\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-b8e6c350\",\n" +
            "            \"name\": \"rabbitmq\",\n" +
            "            \"tags\": [\n" +
            "                \"windows-dc\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-c1e6285f\",\n" +
            "            \"name\": \"k8s node\",\n" +
            "            \"tags\": [\n" +
            "                \"http\",\n" +
            "                \"ci\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-cf1f8621\",\n" +
            "            \"name\": \"k8s node\",\n" +
            "            \"tags\": [\n" +
            "                \"windows-dc\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-b462c04\",\n" +
            "            \"name\": \"jira server\",\n" +
            "            \"tags\": [\n" +
            "                \"windows-dc\",\n" +
            "                \"storage\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-8d2d12765\",\n" +
            "            \"name\": \"kafka\",\n" +
            "            \"tags\": []\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-9cbedf7c66\",\n" +
            "            \"name\": \"etcd node\",\n" +
            "            \"tags\": []\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-ae24e37f8a\",\n" +
            "            \"name\": \"frontend server\",\n" +
            "            \"tags\": [\n" +
            "                \"api\",\n" +
            "                \"dev\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-e30d5fa49a\",\n" +
            "            \"name\": \"etcd node\",\n" +
            "            \"tags\": [\n" +
            "                \"dev\",\n" +
            "                \"api\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-1b1cc9cd\",\n" +
            "            \"name\": \"billing service\",\n" +
            "            \"tags\": []\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-f270036588\",\n" +
            "            \"name\": \"kafka\",\n" +
            "            \"tags\": []\n" +
            "        }\n" +
            "    ],\n" +
            "    \"fw_rules\": [\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-c4a11ac\",\n" +
            "            \"source_tag\": \"k8s\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-1cb4c1\",\n" +
            "            \"source_tag\": \"django\",\n" +
            "            \"dest_tag\": \"django\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-0d91970ee\",\n" +
            "            \"source_tag\": \"corp\",\n" +
            "            \"dest_tag\": \"django\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-778beb64\",\n" +
            "            \"source_tag\": \"django\",\n" +
            "            \"dest_tag\": \"nat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-1008d7\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"ssh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-1c8ebac1f\",\n" +
            "            \"source_tag\": \"loadbalancer\",\n" +
            "            \"dest_tag\": \"corp\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-06bf6a628\",\n" +
            "            \"source_tag\": \"nat\",\n" +
            "            \"dest_tag\": \"nat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-9d030bb4bb\",\n" +
            "            \"source_tag\": \"corp\",\n" +
            "            \"dest_tag\": \"nat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-fbcfcc16e1\",\n" +
            "            \"source_tag\": \"antivirus\",\n" +
            "            \"dest_tag\": \"nat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-c74204\",\n" +
            "            \"source_tag\": \"antivirus\",\n" +
            "            \"dest_tag\": \"antivirus\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static final String INPUT_2 = "{\n" +
            "    \"vms\": [\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-ec02d5c153\",\n" +
            "            \"name\": \"kafka\",\n" +
            "            \"tags\": [\n" +
            "                \"http\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-a3ed2eed23\",\n" +
            "            \"name\": \"rabbitmq\",\n" +
            "            \"tags\": [\n" +
            "                \"https\",\n" +
            "                \"http\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-2ba4d2f87\",\n" +
            "            \"name\": \"ssh bastion\",\n" +
            "            \"tags\": [\n" +
            "                \"http\",\n" +
            "                \"windows-dc\",\n" +
            "                \"nat\",\n" +
            "                \"https\",\n" +
            "                \"storage\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-b35b501\",\n" +
            "            \"name\": \"dev-srv-5\",\n" +
            "            \"tags\": [\n" +
            "                \"ssh\",\n" +
            "                \"nat\",\n" +
            "                \"http\",\n" +
            "                \"loadbalancer\",\n" +
            "                \"storage\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-7d1ff7af47\",\n" +
            "            \"name\": \"billing service\",\n" +
            "            \"tags\": [\n" +
            "                \"http\"\n" +
            "            ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"fw_rules\": [\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-c8706961d\",\n" +
            "            \"source_tag\": \"loadbalancer\",\n" +
            "            \"dest_tag\": \"windows-dc\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-76f36a3\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"ci\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-487b076a6\",\n" +
            "            \"source_tag\": \"storage\",\n" +
            "            \"dest_tag\": \"reverse_proxy\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-dd16d0\",\n" +
            "            \"source_tag\": \"nat\",\n" +
            "            \"dest_tag\": \"ssh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-36719127\",\n" +
            "            \"source_tag\": \"https\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-1f8b1e8d8\",\n" +
            "            \"source_tag\": \"loadbalancer\",\n" +
            "            \"dest_tag\": \"storage\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-e602b7a05\",\n" +
            "            \"source_tag\": \"nat\",\n" +
            "            \"dest_tag\": \"nat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-4e337463\",\n" +
            "            \"source_tag\": \"reverse_proxy\",\n" +
            "            \"dest_tag\": \"storage\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-a646f8da6\",\n" +
            "            \"source_tag\": \"http\",\n" +
            "            \"dest_tag\": \"http\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-28c3124\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"https\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-1310da\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"nat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-64ae2f2be7\",\n" +
            "            \"source_tag\": \"corp\",\n" +
            "            \"dest_tag\": \"nat\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-488809fc3\",\n" +
            "            \"source_tag\": \"corp\",\n" +
            "            \"dest_tag\": \"windows-dc\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-4878f98212\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"ssh\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-1a0642c\",\n" +
            "            \"source_tag\": \"nat\",\n" +
            "            \"dest_tag\": \"corp\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-e6b9108\",\n" +
            "            \"source_tag\": \"windows-dc\",\n" +
            "            \"dest_tag\": \"corp\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static final String INPUT_3 = "{\n" +
            "    \"vms\": [\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-9ea3998\",\n" +
            "            \"name\": \"frontend server\",\n" +
            "            \"tags\": [\n" +
            "                \"antivirus\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-5f3ad2b\",\n" +
            "            \"name\": \"frontend server\",\n" +
            "            \"tags\": [\n" +
            "                \"http\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-d9e0825\",\n" +
            "            \"name\": \"etcd node\",\n" +
            "            \"tags\": [\n" +
            "                \"ssh\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-59574582\",\n" +
            "            \"name\": \"k8s node\",\n" +
            "            \"tags\": [\n" +
            "                \"antivirus\",\n" +
            "                \"ssh\",\n" +
            "                \"api\",\n" +
            "                \"windows-dc\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-f00923\",\n" +
            "            \"name\": \"billing service\",\n" +
            "            \"tags\": [\n" +
            "                \"http\",\n" +
            "                \"dev\",\n" +
            "                \"k8s\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-575c4a\",\n" +
            "            \"name\": \"rabbitmq\",\n" +
            "            \"tags\": [\n" +
            "                \"dev\",\n" +
            "                \"k8s\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-0c1791\",\n" +
            "            \"name\": \"php app\",\n" +
            "            \"tags\": [\n" +
            "                \"http\",\n" +
            "                \"ci\",\n" +
            "                \"reverse_proxy\",\n" +
            "                \"dev\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-2987241\",\n" +
            "            \"name\": \"dev-srv-5\",\n" +
            "            \"tags\": [\n" +
            "                \"k8s\",\n" +
            "                \"api\",\n" +
            "                \"nat\",\n" +
            "                \"reverse_proxy\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-ab51cba10\",\n" +
            "            \"name\": \"ssh bastion\",\n" +
            "            \"tags\": [\n" +
            "                \"https\",\n" +
            "                \"storage\",\n" +
            "                \"loadbalancer\",\n" +
            "                \"corp\",\n" +
            "                \"django\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-a3660c\",\n" +
            "            \"name\": \"frontend server\",\n" +
            "            \"tags\": [\n" +
            "                \"k8s\",\n" +
            "                \"ssh\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"vm_id\": \"vm-864a94f\",\n" +
            "            \"name\": \"kafka\",\n" +
            "            \"tags\": [\n" +
            "                \"dev\"\n" +
            "            ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"fw_rules\": [\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-dd3c1e\",\n" +
            "            \"source_tag\": \"k8s\",\n" +
            "            \"dest_tag\": \"django\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-1688373be0\",\n" +
            "            \"source_tag\": \"antivirus\",\n" +
            "            \"dest_tag\": \"corp\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-f1fcfa\",\n" +
            "            \"source_tag\": \"http\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-93b1338c12\",\n" +
            "            \"source_tag\": \"api\",\n" +
            "            \"dest_tag\": \"corp\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-e1d2dcbf3\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"storage\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-8e836298\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"django\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-742ac1\",\n" +
            "            \"source_tag\": \"k8s\",\n" +
            "            \"dest_tag\": \"django\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-2bf982\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"django\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-9c95744ef\",\n" +
            "            \"source_tag\": \"reverse_proxy\",\n" +
            "            \"dest_tag\": \"django\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-36b8c424e\",\n" +
            "            \"source_tag\": \"nat\",\n" +
            "            \"dest_tag\": \"corp\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-87b059\",\n" +
            "            \"source_tag\": \"api\",\n" +
            "            \"dest_tag\": \"django\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-2d389ea\",\n" +
            "            \"source_tag\": \"ci\",\n" +
            "            \"dest_tag\": \"https\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-3a7c1e8\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"storage\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-3279918d6\",\n" +
            "            \"source_tag\": \"k8s\",\n" +
            "            \"dest_tag\": \"storage\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-dc2742a339\",\n" +
            "            \"source_tag\": \"http\",\n" +
            "            \"dest_tag\": \"django\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-9546840161\",\n" +
            "            \"source_tag\": \"reverse_proxy\",\n" +
            "            \"dest_tag\": \"corp\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-966d9bd4\",\n" +
            "            \"source_tag\": \"api\",\n" +
            "            \"dest_tag\": \"https\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-8ad7dc\",\n" +
            "            \"source_tag\": \"k8s\",\n" +
            "            \"dest_tag\": \"corp\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-6c4649bc\",\n" +
            "            \"source_tag\": \"ci\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-c7ee8f3\",\n" +
            "            \"source_tag\": \"http\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-b3557d17\",\n" +
            "            \"source_tag\": \"windows-dc\",\n" +
            "            \"dest_tag\": \"https\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-fc619d1\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"storage\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-2a83fc853\",\n" +
            "            \"source_tag\": \"ci\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-3069e1653\",\n" +
            "            \"source_tag\": \"api\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-2ab931510\",\n" +
            "            \"source_tag\": \"dev\",\n" +
            "            \"dest_tag\": \"https\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-eb38b5d\",\n" +
            "            \"source_tag\": \"ssh\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-7f0d52f2\",\n" +
            "            \"source_tag\": \"k8s\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-e8cd165c\",\n" +
            "            \"source_tag\": \"ci\",\n" +
            "            \"dest_tag\": \"loadbalancer\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-e85265e1\",\n" +
            "            \"source_tag\": \"api\",\n" +
            "            \"dest_tag\": \"https\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"fw_id\": \"fw-41c837edbb\",\n" +
            "            \"source_tag\": \"ci\",\n" +
            "            \"dest_tag\": \"https\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    @Test
    public void testInput0() throws Exception{
        AttackService service = new AttackService(mapper.readValue(INPUT_0, Conf.class), 100);

        assetContainsOnly(service.attack("vm-a211de"), "vm-c7bac01a07");
        assetContainsOnly(service.attack("vm-c7bac01a07"));
    }

    @Test
    public void testInput1() throws Exception{
        Conf conf = mapper.readValue(INPUT_1, Conf.class);
        AttackService service = new AttackService(conf, 100);
        for(VirtualMachine vm : conf.getVms()){
            assetContainsOnly(service.attack(vm.getId()));
        }
    }

    @Test
    public void testInput2() throws Exception{
        //Test also can't attack itself
        Conf conf = mapper.readValue(INPUT_2, Conf.class);
        AttackService service = new AttackService(conf, 100);

        for(VirtualMachine vm : conf.getVms()){
            Set<String> expected = conf.getVms().stream().map(virtualMachine -> virtualMachine.getId()).collect(Collectors.toSet());
            expected.remove(vm.getId());
            assetContainsOnly(service.attack(vm.getId()), expected.toArray(new String[0]));
        }
    }

    @Test
    public void testInput3() throws Exception {
        //Test also can't attack itself
        Conf conf = mapper.readValue(INPUT_3, Conf.class);
        AttackService service = new AttackService(conf, 100);

        assetContainsOnly(service.attack("vm-864a94f"));
        assetContainsOnly(service.attack("vm-9ea3998"));
        assetContainsOnly(service.attack("vm-5f3ad2b"));
        assetContainsOnly(service.attack("vm-d9e0825"));
        assetContainsOnly(service.attack("vm-f00923"));
        assetContainsOnly(service.attack("vm-575c4a"));
        assetContainsOnly(service.attack("vm-0c1791"));
        assetContainsOnly(service.attack("vm-2987241"));
    }

    /**
     * @param res
     * @param vms
     */
    public static void assetContainsOnly(Set<String> res, String ... vms){
        Assert.assertTrue(res.size() == vms.length);
        Arrays.stream(vms).forEach(vm -> Assert.assertTrue(res.contains(vm)));
    }
}
