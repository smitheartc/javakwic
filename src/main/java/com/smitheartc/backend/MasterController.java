package com.smitheartc.backend;

import java.util.Collection;

import com.smitheartc.backend.cyberminer.SearchHandler;
import com.smitheartc.backend.database.CircularShiftEntity;
import com.smitheartc.backend.kwicSystem.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smitheartc.backend.database.KWICFrontendResponseDTO;
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
	private KWICFrontendResponseDTO kwicFrontendResponseDTO;

    @Autowired
    private SearchHandler searchHandler;


	private void clearMemory() {
		lineStorage.clearMemory();
		circularShift.clearMemory();
		alphabeticShift.clearMemory();
		kwicFrontendResponseDTO.clearMemory();
	}

	



	public static void main(String[] args) {
		SpringApplication.run(MasterController.class, args);
    }

	@CrossOrigin
    @PostMapping("/input")
    public KWICFrontendResponseDTO inputRequest(@RequestBody InputObject inputObject) {

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

		return kwicFrontendResponseDTO;
    }

	@CrossOrigin
	@GetMapping("/indexTable")
	public Collection<String>  getIndexTable() {
		return output.getIndexTable();
	}

	@CrossOrigin
	@PostMapping("/search")
	public Page<CircularShiftEntity> search(@RequestBody SearchObject searchObject) {
        return searchHandler.handleSearch(searchObject);
	}
	





}
