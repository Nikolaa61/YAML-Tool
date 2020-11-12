package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import models.Entity;
import sun.font.CreatedFontTracker;

public class YAMLImplementation extends API
{
	static {
		ToolManager.registerManager(new YAMLImplementation(), ".yaml");
	}
	public YAMLImplementation() {
		super();
	}
	@Override
	public void openFile(File file) 
	{	
		Yaml yaml = new Yaml(new Constructor(Entity.class));
		try 
		{
			InputStream inputStream = new FileInputStream(file);
			
		    for (Object e : yaml.loadAll(inputStream)) 
		    {
		    	if(checkId(((Entity) e).getId()))
		    	{
		    		entities.add((Entity) e);
		    	}
		    }
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		}
		

	}

	@Override
    public void save(List<Entity> data, String fileName) 
    {
        try
        {
            File file = new File(fileName);

            file.createNewFile();

            FileWriter fw = new FileWriter(fileName);
            
            int count = 0;
            for(Entity entity: data)
            {
            	count++;
            	DumperOptions options = new DumperOptions();
            	ConfigurationModelRepresenter customRepresenter = new ConfigurationModelRepresenter();
                Yaml yaml = new Yaml(customRepresenter, options);
                String text = yaml.dumpAs(entity, Tag.MAP, FlowStyle.BLOCK);
                
                if(count == data.size())
                {
                	fw.write(text + "---");
                }
                else
                {
                    fw.write(text + "---\n");
                }
            }
            
            fw.flush();
            fw.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
	
	@Override
	public void brisiKljucVrednost(String name_, String key, String value_, String dirPath) 
	{
		entities.clear();
		
		File directory = new File(dirPath);
		File[] contents = directory.listFiles();
		
		for(File f : contents)
		{
			List<Entity> entities1 = new ArrayList<Entity>();
			
			if(f.length() > 0)
			{
				Yaml yaml = new Yaml(new Constructor(Entity.class));
				
				try 
				{
					InputStream inputStream = new FileInputStream(f);
					
				    for (Object e : yaml.loadAll(inputStream)) 
				    {
				    	Entity entitet = (Entity)e;
		                
				    	boolean found = false;
				    	
				    	for(Map.Entry<String, Object> ent : ((Entity) e).getProperties().entrySet()) 
						{
		                	if(ent.getKey().equals(key))
		                	{
		                		if(ent.getValue().equals(value_) && ((Entity) e).getName().equals(name_))
		                		{
		                			found = true;
		                		}
		                	}
						}
		                
				    	if(!found)
				    	{
							entities1.add(entitet);
				    	}
				    }
				} 
				catch (FileNotFoundException e1) 
				{
					e1.printStackTrace();
				}
			}
			try
			{
				File file = new File(f.getAbsolutePath());
				
				file.createNewFile();
				
				FileWriter fw = new FileWriter(f.getAbsolutePath());
				
				int count = 0;
				
				for(Entity entity: entities1)
				{
					count++;
					DumperOptions options = new DumperOptions();
	            	ConfigurationModelRepresenter customRepresenter = new ConfigurationModelRepresenter();
	                Yaml yaml = new Yaml(customRepresenter, options);
	                String text = yaml.dumpAs(entity, Tag.MAP, FlowStyle.BLOCK);
	                
	                if(count == entities1.size())
	                {
	                	text = text.substring(0, text.length() - 1);
	                	fw.write(text);
	                }
	                else
	                {
	                    fw.write(text + "---\n");
	                }
				}
				fw.flush();
				fw.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			entities.addAll(entities1);
		}
	}
	
	@Override
	public void brisiNaOsnovuIDa(String id, String dirPath) 
	{
		entities.clear();
		
		File directory = new File(dirPath);
		File[] contents = directory.listFiles();
		
		for(File f : contents)
		{
			List<Entity> entities1 = new ArrayList<Entity>();
			
			if(f.length() > 0)
			{
				Yaml yaml = new Yaml(new Constructor(Entity.class));
				
				try 
				{
					InputStream inputStream = new FileInputStream(f);
					
				    for (Object e : yaml.loadAll(inputStream)) 
				    {
				    	Entity entitet = (Entity)e;
				    	if(!entitet.getId().equals(id))
				    	{
							entities1.add(entitet);
				    	}
				    }
				} 
				catch (FileNotFoundException e1) 
				{
					e1.printStackTrace();
				}
			}
			try
			{
				File file = new File(f.getAbsolutePath());
				
				file.createNewFile();
				
				FileWriter fw = new FileWriter(f.getAbsolutePath());
				
				int count = 0;
				
				for(Entity entity: entities1)
				{
					count++;
					DumperOptions options = new DumperOptions();
	            	ConfigurationModelRepresenter customRepresenter = new ConfigurationModelRepresenter();
	                Yaml yaml = new Yaml(customRepresenter, options);
	                String text = yaml.dumpAs(entity, Tag.MAP, FlowStyle.BLOCK);
	                
	                if(count == entities1.size())
	                {
	                	text = text.substring(0, text.length() - 1);
	                	fw.write(text);
	                }
	                else
	                {
	                    fw.write(text + "---\n");
	                }
				}
				fw.flush();
				fw.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			entities.addAll(entities1);
		}
	}
	
	@Override
	public void enterEntityWithID(String tekst, String filePath) 
	{
		try 
		{
			FileWriter f = new FileWriter(new File(filePath), true);
			
			try
			{
				Yaml yaml = new Yaml(new Constructor(Entity.class));
				Entity e = yaml.load(tekst);
				
				if(checkId(e.getId()))
				{
					entities.add(e);
					
					f.write("\n---\n" + tekst);
					f.flush();
					f.close();
				}
				else
				{
					System.out.println("Unet vec posotjeci id");
				}
			}
			catch(Exception e)
			{
				System.out.println("Pogresan format");
			}
		}
		catch (Exception i) 
		{
			i.printStackTrace(); 
		}
	}
	
	private boolean checkId(String id)
	{
		
		for(Entity e : entities)
		{
			if(!e.getEntities().isEmpty())
			{
				for (Map.Entry<String, Entity> entity : e.getEntities().entrySet()) 
				{
					if(entity.getValue().getId().contains(id))
						return false;
				}
			}
			if(e.getId().equals(id))
				return false;
		}
		
		return true;
	}
	
	@Override
	public void enterEntityWithoutID(String tekst, String filePath) 
	{
		Random random = new Random();
		int bound = 999;
		
		int newId = random.nextInt(bound);
		
		String id = Integer.toString(newId);
		
		if(checkId(id) == false)
		{
			boolean bool = checkId(id);
			
			while(bool == false)
			{
				newId = random.nextInt(bound);
				
				id = Integer.toString(newId);
				
				bool = checkId(id);
			}
		}

		try 
		{
			FileWriter f = new FileWriter(new File(filePath), true);
			
			tekst = "id: " + id + "\n" + tekst;
			
			try
			{
				Yaml yaml = new Yaml(new Constructor(Entity.class));
				Entity e = yaml.load(tekst);
			
				entities.add(e);
				
				String txt = "\n---\n" + tekst;
				
				f.write(txt);
				f.flush();
				f.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch (Exception i) 
		{
			i.printStackTrace(); 
		}
	}
}
