package com.fct.d.common;

import com.fct.d.NoSpringBaseTest;
import com.fct.d.lc.PrintLetterInTurn;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author fct
 * @date 2021-07-13 11:14
 */
public class PrintABCTest extends NoSpringBaseTest {

  @Test
  public void printLetterLockTest() throws Exception {
    PrintLetterInTurn loopThread = new PrintLetterInTurn(10);
    List<String> list = Arrays.asList("A", "B", "C");
    for (String s : list) {
      new Thread(
              () -> {
                loopThread.printLetter(s, list.indexOf(s));
              },
              s)
          .start();
    }
    TimeUnit.MINUTES.sleep(1L);
  }

  @Test
  public void printLetterSynchronizedTest() throws Exception {
    PrintLetterInTurn loopThread = new PrintLetterInTurn(10);
    List<String> list = Arrays.asList("A", "B", "C");
    for (String s : list) {
      new Thread(
          () -> {
            try {
              loopThread.printLetter2(s, list.indexOf(s));
            } catch (Exception e) {
              e.printStackTrace();
            }
          },
          s)
          .start();
    }
    TimeUnit.SECONDS.sleep(10L);
  }

}
