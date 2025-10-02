package com.smitheartc.javakwic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smitheartc.javakwic.database.ResponseDTO;
import com.smitheartc.javakwic.database.CircularShiftEntity;

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

		String[] individualLines = inputObject.getInputArray().split("\\$");
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
	





}
