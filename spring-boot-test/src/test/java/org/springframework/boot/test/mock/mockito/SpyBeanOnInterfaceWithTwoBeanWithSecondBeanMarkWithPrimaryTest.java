package org.springframework.boot.test.mock.mockito;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.doReturn;

/**
 * Created by lsiu on 2016-12-14.
 */
@RunWith(SpringRunner.class)
public class SpyBeanOnInterfaceWithTwoBeanWithSecondBeanMarkWithPrimaryTest {

	@Autowired
	private Config.InterfaceAConsumer consumer;

	@SpyBean
	private Config.TheInterface instance;

	@Test
	public void test() {
		doReturn("fromSpyBean").when(instance).giveMeAString();
		Assert.assertThat(consumer.getTheString(), Matchers.equalTo("fromSpyBean"));
	}

	@Configuration
	static class Config {
		public interface TheInterface {
			String giveMeAString();
		}

		@Component
		public static class ClassOne implements TheInterface {
			@Override
			public String giveMeAString() {
				return "fromClassOne";
			}
		}

		@Primary
		@Component
		public static class ClassTwo implements TheInterface {

			@Override
			public String giveMeAString() {
				return "fromClassTwo";
			}
		}

		@Component
		public static class InterfaceAConsumer {
			private final TheInterface theInterface;

			public InterfaceAConsumer(TheInterface interfaceA) {
				this.theInterface = interfaceA;
			}

			String getTheString() {
				return theInterface.giveMeAString();
			}
		}
	}
}
