package MyCode;

public class AcerTest {
	public void print(int n) {
		System.out.println("1=1");
		if(n == 1) {
			return;
		}
		
		int total = 0;
		for(int i = 1; i < n; i++) {
			String tempStr = "";
			for (int j = 0; j < i+1; j++) {
				tempStr +=(int)Math.pow(2, i)+ "+";
			}
			tempStr = tempStr.substring(0,tempStr.length() -1);
			total = (int)Math.pow(2, i) * (i + 1);
			System.out.println(tempStr + "=" + total);
		}
	}
	
	public int maxContinueArray(int[] n) {
		int length = n.length;
		int result = Integer.MIN_VALUE;

		if (length == 0 || length == 1) {
			return 0;
		} else if (length == 2) {
			return n[0] * n[1];
		}

		for (int index = 0; index < length - 1; index++) {
			result = Math.max(result, n[index] * n[index + 1]);
		}

		return result;
	}
	
	
	public int maxContinue(int[] list) {
		int maxResult = Integer.MIN_VALUE;
		int tmpNum = list[0];
		
		
		for(int index = 1;index < list.length;index++) {
			
			if(list[index] == 0) {
				if(index + 1 >= list.length) {
					return maxResult;
				}
				tmpNum = list[++index];
				continue;
			}

			tmpNum *= list[index];
			
			maxResult = Math.max(maxResult, tmpNum);
		}
		return maxResult;
	}
	
	public static void main(String[] args) {
		int[] testList = new int[] {7,-12,0,2,2,3,-6,-11,0};
		AcerTest test = new AcerTest();
//		test.print(16);
		System.out.println(test.maxContinue(testList));
	}
}
