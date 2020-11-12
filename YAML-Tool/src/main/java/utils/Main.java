package utils;

import java.io.File;
import java.util.Random;

public class Main {

	public static void main(String[] args)
	{
//		YAMLImplementation yaml = new YAMLImplementation();
//		yaml.openDir("podaci");
//		yaml.save(yaml.entities, "export.yaml");
//		
		Random random = new Random();
		int bound = 999;
		
		int newId = random.nextInt(bound);
		System.out.println(newId);
	}
}
