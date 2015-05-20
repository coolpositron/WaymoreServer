package com.cloudcomputing.rest.jersey;

import java.util.ArrayList;

public class Snippets {
	private ArrayList<Snippet> snippets = new ArrayList<Snippet>();
	
	public ArrayList<Snippet> getSnippets() {
		return snippets;
	}

	public void setSnippets(ArrayList<Snippet> snippets) {
		this.snippets = snippets;
	}
	
}
