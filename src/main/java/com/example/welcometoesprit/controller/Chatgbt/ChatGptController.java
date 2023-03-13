package com.example.welcometoesprit.controller.Chatgbt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@AllArgsConstructor
public class ChatGptController {

	@Autowired private ObjectMapper jsonMapper;
	@Autowired private OpenAiApiClient client;

	@GetMapping("/EspritBot/{req}")
	public ResponseEntity<List<String>> chatWithGpt3(@PathVariable("req") String message) throws Exception {
		var completion = CompletionRequest.defaultWith("( the context of the question is Esprit Tunisian private school)"+message);
		var postBodyJson = jsonMapper.writeValueAsString(completion);
		var responseBody = client.postToOpenAiApi(postBodyJson, OpenAiApiClient.OpenAiService.GPT_3);
		var completionResponse = jsonMapper.readValue(responseBody, CompletionResponse.class);

		var result = completionResponse.firstAnswer().trim();
		List<String> resultList = Arrays.asList(result.split("\n"));
		resultList.replaceAll(s -> s.replaceAll("^\\d+\\.\\s*", ""));
		return ResponseEntity.ok(resultList);
	}
}
