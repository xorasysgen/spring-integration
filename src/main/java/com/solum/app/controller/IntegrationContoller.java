package com.solum.app.controller;

import static java.lang.Thread.sleep;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solum.app.dto.Product;
import com.solum.app.exception.CommonException;
import com.solum.app.gateway.IntegrationGateway;
import com.solum.app.logic.Env;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/message")
@Slf4j
public class IntegrationContoller {

	@Autowired
	private IntegrationGateway integrationGateway;

	@Autowired
	private Env env;
	// Thread Pool which runs some tasks
	ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

	
	
	@GetMapping("/long-process")
	public String pause() {
		int runUntill=100;
		System.out.println("Thread having PID [" + Thread.currentThread().getId() + "] long process started.." + runUntill + " times should execute");
		try {
			for (int i = 1; i <= runUntill; i++) {
				if(i!=100)
					System.out.println("thread Job PID " + Thread.currentThread().getId() + " processing " + i + "th item out of "+ runUntill);
				else
					System.out.println("thread Job PID " + Thread.currentThread().getId() + " [ Completed ]" + i + "th item out of "+ runUntill + " Thread exited...");
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			throw new CommonException("SERVICE_UNAVAILABLE, Server forcefully shutdown");
		}
		return "Even Though commencing graceful shutdown initiated, Process finished successfully..";
	}
	
	@GetMapping("/task")
	public String hello() throws InterruptedException {
		int i=0;
		log.trace("This is a TRACE message.");
		log.debug("This is a DEBUG message.");
		log.info("This is an INFO message.");
		log.warn("This is a WARN message.");
		log.error("You guessed it, an ERROR message.");
		executor.execute(this::task);
		System.out.println("scheduler started.");
		if(i==0)
			throw new NullPointerException("InterruptedException task");
		else if(i==1)
		throw new CommonException("SERVICE_UNAVAILABLE, Server forcefully shutdown");
		return "Process finished";
	}

	private void task() {
		try {
			System.out.println("finishing pending task");
			sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Completed 1 seconds...");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("PreDestroy Method Triggered.");
		
		System.out.println("|||Pending active thread task completed|||");
	}


	@GetMapping("/shutdown")
	public void shutdownJVM() {
		System.exit(1);
	}

	@GetMapping(value = "{msg}")
	public String getIntegratedMessage(@PathVariable("msg") String message) {
		return integrationGateway.sendMessage(message);

	}

	/* Step 1/7 */
	@PostMapping("/product")
	public String ingestionProduct(@RequestBody Product product) {
		return integrationGateway.processProduct(product);
	}

	public IntegrationGateway getIntegrationGateway() {
		return integrationGateway;
	}

	public void setIntegrationGateway(IntegrationGateway integrationGateway) {
		this.integrationGateway = integrationGateway;
	}

	public Env getEnv() {
		return env;
	}

	public void setEnv(Env env) {
		this.env = env;
	}

}
