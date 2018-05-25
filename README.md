# shiro
1：首先加入pom文件
--------
<!--整合shiro需要的依赖-->

    <dependency>
      <groupId>org.apache.shiro</groupId>
      <artifactId>shiro-core</artifactId>
      <version>1.2.2</version>
    </dependency>
    <dependency>
      <groupId>org.apache.shiro</groupId>
      <artifactId>shiro-web</artifactId>
      <version>1.2.2</version>
    </dependency>
    <dependency>
      <groupId>org.apache.shiro</groupId>
      <artifactId>shiro-spring</artifactId>
      <version>1.2.2</version>
    </dependency>
    <dependency>
      <groupId>org.apache.shiro</groupId>
      <artifactId>shiro-ehcache</artifactId>
      <version>1.2.2</version>
    </dependency>
2：在web.xml中加上shiro的过滤器。
--------
    <!-- Shiro Filter is defined in the spring application context: -->
      <filter>
        <filter-name>shiroFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        <init-param>
          <param-name>targetFilterLifecycle</param-name>
          <param-value>true</param-value>
        </init-param>
      </filter>
      <filter-mapping>
        <filter-name>shiroFilter</filter-name>
        <url-pattern>/*</url-pattern>
3：写shiro的配置文件。spring-shiro.xml
----------------
    <!--
        1. 配置 SecurityManager!
        -->
        <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
            <property name="cacheManager" ref="cacheManager"/>
            <property name="sessionMode" value="native"/>
            <property name="realm" ref="jdbcRealm"/>
        </bean>
        <!--
        2. 配置 CacheManager.
        2.1 需要加入 ehcache 的 jar 包及配置文件.
        -->
        <bean id="cacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
        </bean>

        <!--
          3. 配置 Realm
          3.1 直接配置实现了 org.apache.shiro.realm.Realm 接口的 bean
        -->
        <bean id="jdbcRealm" class="com.shiro.realms.ShiroRealm">

        </bean>

        <!--
        4. 配置 LifecycleBeanPostProcessor. 可以自定的来调用配置在 Spring IOC 容器中 shiro bean 的生命周期方法.
        -->
        <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>

        <!--
        5. 启用 IOC 容器中使用 shiro 的注解. 但必须在配置了 LifecycleBeanPostProcessor 之后才可以使用.
        -->
        <bean class="org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"
              depends-on="lifecycleBeanPostProcessor"/>
        <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
            <property name="securityManager" ref="securityManager"/>
        </bean>

        <!--
        6. 配置 ShiroFilter.
        6.1 id 必须和 web.xml 文件中配置的 DelegatingFilterProxy 的 <filter-name> 一致.
                          若不一致, 则会抛出: NoSuchBeanDefinitionException. 因为 Shiro 会来 IOC 容器中查找和 <filter-name> 名字对应的 filter bean.
        -->
        <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
            <property name="securityManager" ref="securityManager"/>
            <property name="loginUrl" value="/login.jsp"/>
            <property name="successUrl" value="/list.jsp"/>
            <property name="unauthorizedUrl" value="/unauthorized.jsp"/>

            <!--
              配置哪些页面需要受保护.
              以及访问这些页面需要的权限.
              1). anon 可以被匿名访问
              2). authc 必须认证(即登录)后才可能访问的页面.
              3). logout 登出.
              4). roles 角色过滤器
            -->
            <property name="filterChainDefinitions">
                <value>
                    /login.jsp = anon

                    # everything else requires authentication:
                    /** = authc
                </value>
            </property>
        </bean>
4：项目加载的时候启动spring-shiro.xml文件
--------
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml,classpath:spring-shiro.xml</param-value>
      </context-param>
