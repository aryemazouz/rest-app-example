package com.rest.app;

import com.rest.app.model.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StatsService {

    //
    //seconds.millis
    private DecimalFormat timeFormat = new DecimalFormat("#.##################");

    /**
     * This is not 100% accurate metrics
     * But to avoid synchronization / locks / third-party jars it is good enough
     * On new data we first update in that order:
     *   ~ {@link #totalDuration}
     *   ~ {@link #totalCounter}
     * But when we calculate the statistics, we read {@link #totalCounter} first.
     * If thread updated the {@link #totalCounter} we can be sure the duration is already updated.
     * Therefore we may return longer average value than the actual real average value.  */
    private static AtomicLong totalCounter = new AtomicLong();
    private static AtomicLong totalDuration = new AtomicLong();

    @Autowired
    private AttackService attackService;

    /**
     * @param nanoDuration
     */
    public void addRequest(long nanoDuration) {
        totalDuration.addAndGet(nanoDuration);
        totalCounter.incrementAndGet();
    }

    /**
     * @return
     */
    public Stats getCurrentStats() {

        Stats stats = new Stats();

        long counter = totalCounter.get();

        double average = 0;
        if(counter > 0){
            Duration duration = Duration.ofNanos(totalDuration.get() / counter);
            average = duration.getSeconds() + ((double)duration.getNano() / (double)1000000000);
        }

        stats.put("vm_count", attackService.getVmsCount());
        stats.put("request_count", counter);
        stats.put("average_request_time", timeFormat.format(average));

        return stats;
    }
}
