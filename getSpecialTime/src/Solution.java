/**
 * The clock distinct solution. Get the time with max unique number as 2
 * Example: Time 12:12:00 to 12:13:00, get time: 12:12:11, 12:12:12, 12:12:21, 12:12:22
 * Run: java getSpecialTime/src/Solution.java
 */
public class Solution {

    /**
     * @param String S start time
     * @param String T end time
     * @return int The matched time number
     */
    public int solution(String S, String T) {
        java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat("hh:mm:ss");
        java.util.Date startTime;
        java.util.Date endTime;

        try {
            startTime = timeFormat.parse(S);
            endTime = timeFormat.parse(T);
        } catch (java.text.ParseException e) {
            throw new IllegalArgumentException("Wrong parameters that you input");
        }

        if (startTime.compareTo(endTime) >= 0) {
            throw new IllegalArgumentException("The end time must larger than the start time");
        }

        int results = 0;
        int timeDiffer = (int) (endTime.getTime() - startTime.getTime()) / 1000;
        for (int i = 0; i <= timeDiffer; i ++) {
            startTime.setTime(startTime.getTime() + 1000);
            String time = timeFormat.format(startTime);
            if (time.chars().distinct().count() <= 3) {
//                System.out.println("Time: " + time);
                results++;
            }
        }

        return results;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        if (4 != s.solution("12:12:00", "12:13:00")) {
            System.out.println("The results not expected");
        }

        try {
            System.out.println(s.solution("MOCKED_WRONG_TIME", "12:14:00"));
            System.out.println("Failed get exception when put a wrong format time");
        } catch (IllegalArgumentException exception) {
        }

        try {
            System.out.println(s.solution("14:12:00", "12:14:00"));
            System.out.println("Failed get exception when end time smaller than the start time");
        } catch (IllegalArgumentException exception) {
        }

        System.out.println("Done!");
    }
}
