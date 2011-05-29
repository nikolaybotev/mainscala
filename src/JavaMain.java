import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class JavaMain extends Random {

	private static final long serialVersionUID = -7308650988223544294L;


	public static void main(String[] args) {
//		Class<JavaMain> cl = JavaMain.class;
//		System.out.println(cl);
//		System.out.println(cl.getSuperclass());
//		System.out.println(cl.getSuperclass().getSuperclass());
//		Class<? extends Class> cll = cl.getClass();
//		System.out.println(cll);
//		Class<? extends Class> clll = cll.getClass();
//		System.out.println(clll);
//		System.out.println(clll);
//		Class xxx = null;

		JavaMain main = new JavaMain();
		System.out.println("digraph {");
		System.out.printf("  \"javamain\" -> \"class %s\" [label=\"  is of\"];\n", main.getClass().getSimpleName());
		main.startVisit(main.getClass());
		System.out.println("  \"null\" [shape=square];");
		System.out.println("}");
	}
	
	private final Set<String> isa = new HashSet<String>();
	
	private final Set<String> ext = new HashSet<String>();


	public void startVisit(Class<?> cl) {
		visitIsA(cl, cl.getClass());
		visitSuper(cl, cl.getSuperclass());
	}
	
	private void visitIsA(Class<?> parent, Class<?> cl) {
		String assoc = String.format("  \"class %s\" -> \"class %s\" [label=\"  is of\"];", parent.getSimpleName(), cl.getSimpleName());
		if (isa.contains(assoc)) return;
		
		System.out.println(assoc);
		isa.add(assoc);
		
		startVisit(cl);
	}



	private void visitSuper(Class<?> parent, Class<?> cl) {
		
		String assoc = String.format("  \"class %s\" -> \"%s\" [label=\"  extends\",color=\"blue\"];", parent.getSimpleName(), cl == null ? "null" : "class " + cl.getSimpleName());
		if (ext.contains(assoc)) return;
		System.out.println(assoc);
		ext.add(assoc);
		
		if (cl == null) return;
		
		startVisit(cl);
	}
	
}
