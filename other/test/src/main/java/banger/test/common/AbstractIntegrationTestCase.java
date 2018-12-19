package banger.test.common;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


@ContextConfiguration(locations={"classpath*:/AbstractIntegrationTestCase-context.xml"})
public abstract class AbstractIntegrationTestCase extends AbstractTransactionalJUnit4SpringContextTests {

}
