#Duplicate code 1:

##Codigo inicial
 en src/main/java/dataAccess/ObjectdbManagerServer.java metodo ObjectdbManagerServer:
 //line 76
try {
  Runtime.getRuntime().exec("java -cp "+objectDbpath+" com.objectdb.Server -port "+ c.getDatabasePort()+" stop");
} catch (Exception ioe) {
    System.out.println (ioe);
  }

//line 113
try {
  Runtime.getRuntime().exec("java -cp "+objectDbpath+" com.objectdb.Server -port "+ c.getDatabasePort()+" start");
  } catch (Exception ioe) {
      System.out.println (ioe);
	}
'
##Codigo refactorizado:

executeCommand("start")
executeCommand("stop")

public void executeCommand(String action) {
  try {
    Runtime.getRuntime().exec("java -cp "+objectDbpath+" com.objectdb.Server -port "+ c.getDatabasePort()+" " + action);
	} catch (Exception ioe) {
		  System.out.println (ioe);
	  }
}

##Descripción:
El bad smell detectado es el código duplicado. En el método ObjectdbManagerServer() en dos ocasiones se repiten varias lineas
de codigo para ejecutar el mismo comando que únicamente cambia en la accion (start/stop). En la refactorización se ha creado
un método adicional executeCommand al que se le pasa por parámetro la acción en forma de string. En el método original se ha
sustituido las lineas de codigo repetido por la llamada al nuevo método.
