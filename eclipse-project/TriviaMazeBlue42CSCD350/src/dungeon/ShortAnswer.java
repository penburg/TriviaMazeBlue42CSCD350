package dungeon;

public class ShortAnswer extends Question
{
	
	private String correctAnswer;
	
	public ShortAnswer(String shortAnswer, String question, String explanation)
	{
		this.question = question;
		this.explanation = explanation;
		this.correctAnswer = shortAnswer;
	}
	
	public String getCorrectAnswer()
	{
		return this.correctAnswer;
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