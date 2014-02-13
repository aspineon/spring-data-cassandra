package org.springframework.data.cassandra.test.integration.mappingcontext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.data.cassandra.mapping.CassandraPersistentEntity;
import org.springframework.data.cassandra.mapping.DefaultCassandraMappingContext;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.data.mapping.model.MappingException;

public class MappingContextIntegrationTests {

	public static class Transient {
	}

	@Table
	public static class X {
		@PrimaryKey
		String key;
	}

	@Table
	public static class Y {
		@PrimaryKey
		String key;
	}

	DefaultCassandraMappingContext ctx = new DefaultCassandraMappingContext();

	@Test(expected = MappingException.class)
	public void testGetPersistentEntityOfTransientType() {

		CassandraPersistentEntity<?> entity = ctx.getPersistentEntity(Transient.class);

	}

	@Test
	public void testGetExistingPersistentEntityHappyPath() {

		ctx.getPersistentEntity(X.class);

		assertTrue(ctx.contains(X.class));
		assertNotNull(ctx.getExistingPersistentEntity(X.class));
		assertFalse(ctx.contains(Y.class));
	}
}
