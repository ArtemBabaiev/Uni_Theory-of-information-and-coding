package edu.toiac.lab6;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HammingCell{
	String bin;
	Integer data;
	BitType type;
}