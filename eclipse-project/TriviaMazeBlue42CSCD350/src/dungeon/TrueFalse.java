package dungeon;

public class TrueFalse extends Question
{

	private String btn1, btn2;
	private int correctBtn;
	
	public TrueFalse(int correctAnswer, String question, String explanation)
	{
		this.question = question;
		this.explanation = explanation;
		this.btn1 = "True";
		this.btn2 = "False";
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