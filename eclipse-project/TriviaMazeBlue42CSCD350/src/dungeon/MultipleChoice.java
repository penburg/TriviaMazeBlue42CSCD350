package dungeon;

public class MultipleChoice extends Question
{
	
	private String btn1, btn2, btn3, btn4;
	private int correctBtn;
	
	public MultipleChoice(String btn1, String btn2, String btn3, String btn4, int correctAnswer, String question, String explanation)
	{
		this.question = question;
		this.explanation = explanation;
		this.btn1 = btn1;
		this.btn2 = btn2;
		this.btn3 = btn3;
		this.btn4 = btn4;
		this.correctBtn = correctAnswer;
	}
	
	public String getBtn1()
	{
		return this.btn1;
	}
	
	public String getBtn2()
	{
		return this.btn2;
	}
	
	public String getBtn3()
	{
		return this.btn3;
	}
	
	public String getBtn4()
	{
		return this.btn4;
	}
	
	public int getCorrectAnswer()
	{
		return this.correctBtn;
	}
	
	public String getQuestion()
	{
		return this.question;
	}
	
	public String getExplanation()
	{
		return this.explanation;
	}

	@Override
	public void onUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnter() {
		// TODO Auto-generated method stub
		
	}
	
}