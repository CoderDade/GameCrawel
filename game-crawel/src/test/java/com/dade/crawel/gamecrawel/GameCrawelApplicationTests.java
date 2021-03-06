package com.dade.crawel.gamecrawel;

import com.dade.crawel.gamecrawel.pool.LOLUserIdPool;
import com.dade.crawel.gamecrawel.service.LOLCrawelParallelService;
import com.dade.crawel.gamecrawel.service.LOLCrawelService;
import com.google.common.collect.Sets;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameCrawelApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSplit(){
		String str = "abc";
		List<String> list = Lists.newArrayList(str.split("\\,"));
		list.forEach(System.out::println);
	}

	@Autowired
	LOLCrawelService lolCrawelService;

	@Test
	public void run(){
		lolCrawelService.crawel();
	}

	@Test
	public void testPool(){
		LOLUserIdPool pool = LOLUserIdPool.getInstance();
		System.out.println(pool.getUserId());
	}

	@Autowired
	LOLCrawelParallelService lolCrawelParallelService;

	@Test
	public void testRun(){
		lolCrawelParallelService.threadRun();
	}

	@Test
	public void testDifference(){
		Set<String> big = Sets.newHashSet("1","2","3");
		Set<String> small = Sets.newHashSet("1","2");
		big = Sets.difference(big, small);
		System.out.println(big);

	}


}
