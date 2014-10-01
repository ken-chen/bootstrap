package myToolsTestNG;

import org.junit.Test;

public class SampleTest {
/*
//	
//	@Test
	public void test2(){
		System.out.println("aaa");
		String urls= "I+just+checked+out+Holden%27s+VF+Commodore+page+and+thought+you%27d+be+interested+too%21+Follow+the+link+to+have+a+look+for+yourself.";
		
		String filterMessage = urls.replaceAll("%27", "'").replaceAll("%21", "!").replaceAll("\\+", " ");
		
		System.out.println(filterMessage);
		System.out.println("BBB");
	}
	
	
	//@Test
	public void test(){
		System.out.println("aaa");
		//String urls= "http://twitter.com/intent/tweet?text=Holden+VF+Commodore.+Breaks+new+ground%2C+Smashes+old+perceptions%21&url=http%3A%2F%2Fbit.ly%2F18MrpKe&hashtags=vfcommodore,changesminds&original=";
		//String urls ="http://twitter.com/intent/tweet?text=The+%23HoldenBarina+is+the+ultimate+car+for+a+modern+urban+lifestyle.+Check+it+out%21&amp;url=http%3A%2F%2Fwww.holden.com.au%2Fcars%2Fbarina&amp;via=holdennews&amp;original";
		//String urls ="http://twitter.com/intent/tweet?text=The+%23HoldenMalibu+is+pretty+impressive.+Why+not+have+a+look+for+yourself%3F&amp;url=http%3A%2F%2Fbit.ly%2F128oF9W&amp;via=holdennews&amp;hashtags=ThinkHolden,holdenmalibu&amp;original=";
		String urls ="http://twitter.com/intent/tweet?text=The+bold+%26+powerful+%23HoldenCaptiva7+arrives%2C+the+new+SUV+king%2C+family+tailored+and+a+great+drive+%40holdennews&amp;url=http%3A%2F%2Fbit.ly%2F19RDWtN&amp;original=";
		String filterMessage = urls.replaceAll("%27", "'").replaceAll("%21", "!").replaceAll("\\+", " ").replaceAll("%2C", ",").replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("&amp;", "&").replaceAll("%23", "#").replaceAll("%3F", "?");
		//System.out.println(filterMessage);
		
		String[] filterMessages  = filterMessage.split("&");
		//System.out.println(filterMessages.length);
		String tweetMessage = filterMessages[0].replace("http://twitter.com/intent/tweet?text=","").replaceAll("%26", "&").replaceAll("%40", "@");
	
		String tweetUrl = filterMessages[1].replace("url=","");
		String tweetHashTag = null;
		if(filterMessages.length >2){
			for(int i=2; i <filterMessages.length;i++){
				if (filterMessages[i].contains("hashtags")){
					 tweetHashTag = filterMessages[i].replace("hashtags=","").replace("via=", "").replace("original=", "");
				}
				
			}
		}
		System.out.println(tweetMessage);
		System.out.println(tweetUrl);
		System.out.println(tweetHashTag);
		
		System.out.println("BBB");
		System.out.println(System.currentTimeMillis());
		
	}
*/	
	
	@Test
	public void sampleTest(){
		String input = "http://platform.twitter.com/widgets/tweet_button.1397165098.html#_=1397968104473&count=horizontal&counturl=http%3A%2F%2Fwww.holden.com.au%2Fcs%2Fcars%2Fbarina-spark&hashtags=holden%2Cbarinaspark&id=twitter-widget-0&lang=en&original_referer=http%3A%2F%2Fwww.holden.com.au%2Fcars%2Fbarina-spark&size=m&text=The%20Holden%20Barina%20Spark%20is%20pretty%20impressive.%20Why%20not%20have%20a%20look%20for%20yourself%3F&url=http%3A%2F%2Fbit.ly%2FcNOXUW&via=holdennews";
		String filterMessage = input.replaceAll("%27", "'").replaceAll("%21", "!").replaceAll("\\+", " ").replaceAll("%2C", ",").replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("&amp;", "&").replaceAll("%23", "#").replaceAll("%3F", "?").replace("%20", " ");
		String[] filterMessages  = filterMessage.split("&");
		
		String tweetHashTag = null;
		String tweetUrl = null;
		String tweetText = null;
		for(int i=0; i <filterMessages.length;i++){
			if (filterMessages[i].startsWith("url")){
				tweetUrl = filterMessages[i].replace("url=","");
					//System.out.println(tweetUrl);
			}else if (filterMessages[i].contains("hashtags")){
				 tweetHashTag = filterMessages[i].replace("hashtags=","").replace("via=", "").replace("original=", "");
					//System.out.println(tweetHashTag);
			}else if (filterMessages[i].contains("text")){
				tweetText = filterMessages[i].replace("text=","");
				System.out.println(tweetText);
			}
			//System.out.println(filterMessages[i]+"=======");
		
		
		}
	}
	
}
