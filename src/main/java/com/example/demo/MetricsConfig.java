/*-------------------------------------------------------------------------------
 * Copyright (C) BIPROGY Inc. All rights reserved.
 * since    : 2024/08/10
 *-------------------------------------------------------------------------------
 *-----------------------------------------------------------------------------*/

package com.example.demo;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.cloudwatch2.CloudWatchConfig;
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.ProxyConfiguration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;


@Configuration
public class MetricsConfig {
    @Bean
    public CloudWatchAsyncClient cloudWatchAsyncClient() {

        SdkAsyncHttpClient sdkAsyncHttpClient = NettyNioAsyncHttpClient.builder()
                .proxyConfiguration(ProxyConfiguration.builder()
                        .scheme("http")
                        .host("proxy.unisys.co.jp")
                        .port(8080)
                        .build())
                .build();

        return CloudWatchAsyncClient
                .builder()
                .region(Region.AP_NORTHEAST_1)
                .httpClient(sdkAsyncHttpClient)
                .build();
    }

    @Bean
    public MeterRegistry getMeterRegistry() {
        CloudWatchMeterRegistry cloudWatchMeterRegistry =
                new CloudWatchMeterRegistry(
                        setupCloudWatchConfig(),
                        Clock.SYSTEM,
                        cloudWatchAsyncClient());

        return cloudWatchMeterRegistry;
    }

    private CloudWatchConfig setupCloudWatchConfig() {
        CloudWatchConfig cloudWatchConfig = new CloudWatchConfig() {

            private Map<String, String> configuration = new HashMap<>(){{
                put("cloudwatch.namespace", "front");
//                put("cloudwatch.dimension", "app");
                put("cloudwatch.step", Duration.ofMinutes(1).toString());
//                put("cloudwatch.enabled", "false");
            }};

            @Override
            public String get(String key) {
                return configuration.get(key);
            }

            @Override
            public boolean enabled() {
                return CloudWatchConfig.super.enabled();
            }
        };
        return cloudWatchConfig;
    }
}
