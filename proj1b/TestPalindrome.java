import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String a = "a";
        String b = "ab";
        String c = "racar";
        String d = "raccar";
        String e = "";

        assertTrue(palindrome.isPalindrome(a));
        assertFalse(palindrome.isPalindrome(b));
        assertTrue(palindrome.isPalindrome(c));
        assertTrue(palindrome.isPalindrome(d));
        assertTrue(palindrome.isPalindrome(e));
    }

    @Test
    public void testIsPalindrome2() {
        String a = "%";
        String b = "ace";
        String c = "bfc";
        String d = "flake";
        String e = "";

        CharacterComparator cc = new OffByOne();

        assertTrue(palindrome.isPalindrome(a,cc));
        assertFalse(palindrome.isPalindrome(b,cc));
        assertTrue(palindrome.isPalindrome(c,cc));
        assertTrue(palindrome.isPalindrome(d,cc));
        assertTrue(palindrome.isPalindrome(e,cc));
    }
}
