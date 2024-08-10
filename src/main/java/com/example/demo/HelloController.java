/*-------------------------------------------------------------------------------
 * Copyright (C) BIPROGY Inc. All rights reserved.
 * since    : 2024/08/10
 *-------------------------------------------------------------------------------
 *-----------------------------------------------------------------------------*/

package com.example.demo;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private MeterRegistry meterRegistry;

    private Counter counter;

    @RequestMapping(method= RequestMethod.GET)
    public String hello() {


        ///////////////////////////////////////////////////////////////////
        //////////////////  Micrometer Test Start /////////////////////////
        ///////////////////////////////////////////////////////////////////

        if (counter == null) {
            counter = meterRegistry.counter("ap.credit.success");
        }
        counter.increment();

        Metrics.addRegistry(new SimpleMeterRegistry());
        Metrics.counter("regi.counter").increment();
        double count = Metrics.counter("regi.counter").count();

        System.out.println("////////////////////////////////////////////////\n" + "Micrometer Test:[" + count + "]\n"
                + "////////////////////////////////////////////////");

        ///////////////////////////////////////////////////////////////////
        //////////////////   Micrometer Test End  /////////////////////////
        ///////////////////////////////////////////////////////////////////

        return "Hello Spring MVC";
    }
}