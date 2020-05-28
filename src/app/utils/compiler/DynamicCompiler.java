package app.utils.compiler;

import javax.tools.*;

import app.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DynamicCompiler {

	private JavaFileManager fileManager;
	private JavaCompiler compiler;

	private String fullName;

	public DynamicCompiler() {
		this.fileManager = initFileManager();
	}

	public JavaFileManager initFileManager() {
		compiler = ToolProvider.getSystemJavaCompiler();
		fileManager = new ClassFileManager(compiler.getStandardFileManager(null, null, null));
		return fileManager;
	}

	public boolean compile(String fullName, String sourceCode) {
		this.fullName = fullName;
		List<JavaFileObject> files = new ArrayList<>();
		files.add(new CharSequenceJavaFileObject(fullName, sourceCode));

		List<String> arguments = new ArrayList<>();
		try {
			arguments.add("-classpath");
			arguments.add(getJar().getAbsolutePath());
		} catch (URISyntaxException e) {
			Utils.getLogger().error(e);
		}

		return compiler.getTask(null, fileManager, null, arguments, null, files).call();
	}

	public static File getJar() throws URISyntaxException {
	  return new File( DynamicCompiler.class.getProtectionDomain().getCodeSource().getLocation().toURI());
  }

	public Method getBinder(String name) {
		try {
			Method[] methods = fileManager.getClassLoader(null)
			                        			.loadClass(fullName)
																		.getMethods();
			return Arrays.asList(methods)
									 .stream()
									 .filter(m -> m.getName().equals(name) )
									 .collect(Collectors.toList())
									 .get(0);
		} catch (SecurityException | ClassNotFoundException e) {
			Utils.getLogger().error(e);
		} 
		return null;
	}

	public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

			private CharSequence content;

			public CharSequenceJavaFileObject(String className, CharSequence content) {
					super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
					this.content = content;
			}

			public CharSequence getCharContent(boolean ignoreEncodingErrors) {
					return content;
			}
	}

	public class ClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {
		private JavaClassObject javaClassObject;

		public ClassFileManager(StandardJavaFileManager standardManager) {
			super(standardManager);
		}

		@Override
		public ClassLoader getClassLoader(Location location) {
			return new SecureClassLoader() {
				@Override
				protected Class<?> findClass(String name) throws ClassNotFoundException { 
					if(name == fullName) {
						byte[] b = javaClassObject.getBytes();
						return super.defineClass(name, javaClassObject.getBytes(), 0, b.length);									
					}
					return Class.forName(name);
				}
			};
		}

		public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
			this.javaClassObject = new JavaClassObject(className, kind);
			return this.javaClassObject;
		}
	}

	public class JavaClassObject extends SimpleJavaFileObject {
		protected final ByteArrayOutputStream bos =	new ByteArrayOutputStream();

		public JavaClassObject(String name, Kind kind) {
			super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
		}

		public byte[] getBytes() {
			return bos.toByteArray();
		}

		@Override
		public OutputStream openOutputStream() throws IOException {
			return bos;
		}
	}
}
