package com.samsung.dagger2sample.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roman on 16. 5. 27.
 */
@Module
public class ThreadPoolModule {

    @Provides
    @Singleton
    ExecutorService provideThreadPool() {
        return Executors.newSingleThreadExecutor();
    }
}
