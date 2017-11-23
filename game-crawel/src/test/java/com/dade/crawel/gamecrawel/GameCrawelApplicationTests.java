package com.dade.crawel.gamecrawel;

import com.dade.crawel.gamecrawel.pool.LOLCrawelPool;
import com.dade.crawel.gamecrawel.service.LOLCrawelService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
		LOLCrawelPool pool = LOLCrawelPool.getInstance();
		System.out.println(pool.getUserId());
	}

}
