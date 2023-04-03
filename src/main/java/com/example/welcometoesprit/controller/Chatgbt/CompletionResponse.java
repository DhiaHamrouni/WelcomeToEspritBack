package com.example.welcometoesprit.controller.Chatgbt;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record CompletionResponse(Usage usage, List<Choice> choices) {

	@NotNull
	public String firstAnswer()
	{
		if (choices == null || choices.isEmpty())
			return "no response";
		return choices.get(0).text;
	}
	
	record Usage(int total_tokens, int prompt_tokens, int completion_tokens) {}
	
	record Choice(String text) {}
}
