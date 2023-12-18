import java.util.Stack;

public class Verifier 
{
	// Some texts and the expected outcome
	public static void main(String [] args)
	{
		System.out.println(verify("left\\{ aa \\{ bb { cc } dd \\} ee \\right\\}")); // true
		System.out.println(verify("\\left\\{ aa \\right\\} bb \\{ cc { dd {  ee } ff \\{ gg \\} } hh \\}")); // true
		System.out.println(verify("\\left\\{aaa\\right\\} bbb \\{ccc{dd{eeee}f\\{g\\}hh\\}")); // false
	}
	
	public static boolean verify(String text)
	{
		// puts the characters of the text in an array of type String and realizes a Stack object
		String [] characters = text.split("");
		Stack<String> stack = new Stack<>();

		for(String character: characters){

			if(character.equals("{")){
				stack.push("{");
			}

			// checks whether the text is well-formed, if so pops the top String from the stack
			if(character.equals("}")){
				if(stack.empty()){
					return false;
				}
				if(!(stack.peek().equals("{"))){
					return false;
				}	else {
					stack.pop();
				}
			}
		}

		return stack.empty();
	}
}
