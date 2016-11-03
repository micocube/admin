package com.jpa.util;
import java.io.*;
import java.util.*;
import javax.tools.*;
import javax.tools.JavaFileManager.*;

/**
 * Author: Jiangtao He; Email: ross.jiangtao.he@gmail.com
 * Date: 2012-2-2
 * Since: MyJavaExpert v1.0
 * Description: Compile the java source code to java class
 */
public class JCompiler
{
    /**
     * Author: Jiangtao He; Email: ross.jiangtao.he@gmail.com
     * @param sFullFileName: the java source file name with full path
     * @return bRet: true-compile successfully, false - compile unsuccessfully
     * Description: Compile java source file to java class with run method
     */
    public static boolean compileFile(String sFullFileName)
    {
        boolean bRet = false;
        // get compiler
        JavaCompiler oJavaCompiler = ToolProvider.getSystemJavaCompiler();
        // compile the java source code by run method
        int iCompileRet = oJavaCompiler.run(null, null, null, sFullFileName);
        // set compile result
        if (0 == iCompileRet)
        {
            bRet = true;
        }
        return bRet;
    }
    
    public static void main(String args[]){
    	JCompiler.compileFile("D:\\apache-tomcat-7.0.72\\webapps\\Mico\\WEB-INF\\classes\\com\\Mico\\controller\\Test.java");
    }

    /**
     * Author: Jiangtao He; Email: ross.jiangtao.he@gmail.com
     * @param sFullFileName: the java source file name with full path
     * @param sOutputPath: the output path of java class file
     * @return bRet: true-compile successfully, false - compile unsuccessfully
     * Description: Compile java source file to java class with getTask
     *     method, it can specify the class output path and catch diagnostic
     *     information
     * @throws IOException 
     */
    public boolean compileFile(String sFullFileName, String sOutputPath) throws IOException
    {
        boolean bRet = false;
        // get compiler
        JavaCompiler oJavaCompiler = ToolProvider.getSystemJavaCompiler();

        // define the diagnostic object, which will be used to save the
        // diagnostic information
        DiagnosticCollector<JavaFileObject> oDiagnosticCollector = new DiagnosticCollector<JavaFileObject>();

        // get StandardJavaFileManager object, and set the diagnostic for the
        // object
        StandardJavaFileManager oStandardJavaFileManager = oJavaCompiler
                .getStandardFileManager(oDiagnosticCollector, null, null);

        // set class output location
        Location oLocation = StandardLocation.CLASS_OUTPUT;
        try
        {
            oStandardJavaFileManager.setLocation(oLocation, Arrays
                    .asList(new File[] { new File(sOutputPath) }));

            // get JavaFileObject object, it will specify the java source file.
            Iterable<? extends JavaFileObject> oItJavaFileObject = oStandardJavaFileManager
                    .getJavaFileObjectsFromFiles(Arrays.asList(new File(
                            sFullFileName)));

            // compile the java source code by using CompilationTask's call
            // method
            bRet = oJavaCompiler.getTask(null, oStandardJavaFileManager,
                    oDiagnosticCollector, null, null, oItJavaFileObject).call();

            //print the Diagnostic's information
            for (Diagnostic<?> oDiagnostic : oDiagnosticCollector
                    .getDiagnostics())
            {
                System.out.println("Error on line: "
                        + oDiagnostic.getLineNumber() + "; URI: "
                        + oDiagnostic.getSource().toString());
            }
        }
        catch (IOException e)
        {
            //exception process
            System.out.println("IO Exception: " + e);
            throw e;
        }
        finally
        {
            //close file manager
            if (null != oStandardJavaFileManager)
            {
                oStandardJavaFileManager.close();
            }
        }
        return bRet;
    }
}