package com.smitheartc.backend;

import java.util.Collection;

import com.smitheartc.backend.kwicSystem.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smitheartc.backend.database.ResponseDTO;
import com.smitheartc.backend.database.SearchObject;
import com.smitheartc.backend.database.InputObject;

@SpringBootApplication
@RestController
public class MasterController {

	@Autowired
	private Input input;

	@Autowired 
	private LineStorage lineStorage;

	@Autowired
	private CircularShift circularShift;

	@Autowired
	private Output output;

	@Autowired
	private AlphabeticShift alphabeticShift;

	@Autowired
	private ResponseDTO responseDTO;


	private void clearMemory() {
		lineStorage.clearMemory();
		circularShift.clearMemory();
		alphabeticShift.clearMemory();
		responseDTO.clearMemory();
	}

	



	public static void main(String[] args) {
		SpringApplication.run(MasterController.class, args);
    }

	@CrossOrigin
    @PostMapping("/input")
    public ResponseDTO inputRequest(@RequestBody InputObject inputObject) {

		clearMemory();
		
		int lineCount = 0;

		System.err.println("Here's what we got: " + inputObject.getInputArray());

		String[] individualLines = inputObject.getInputArray().split("\n");
        for (String itemToRead : individualLines) { //incremental instead of batch processing
			input.read(itemToRead);
	        circularShift.addLine(lineCount);
			alphabeticShift.alpha();
			lineCount++;
        }

		output.storeResults();

		return responseDTO;
    }

	@CrossOrigin
	@GetMapping("/indexTable")
	public Collection<String>  getIndexTable() {
		return output.getIndexTable();
	}

	@CrossOrigin
	@PostMapping("/search")
	public Collection<String> search(@RequestBody SearchObject searchObject) {
		Collection<String> obj = output.getIndexTable();
		Class<?> objectClass = obj.getClass();
		System.err.println(objectClass.getName()); // Output: java.lang.String
		System.err.println(objectClass.getSimpleName()); // Output: String
		return obj;
	}
	





}
