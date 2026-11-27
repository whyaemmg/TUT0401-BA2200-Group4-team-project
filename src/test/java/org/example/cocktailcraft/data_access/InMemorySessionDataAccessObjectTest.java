package org.example.cocktailcraft.data_access;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemorySessionDataAccessObjectTest {

    private InMemorySessionDataAccessObject session;

    @BeforeEach
    void setUp() {
        session = new InMemorySessionDataAccessObject();
    }

    @Test
    void testInitialSession() {
        assertNull(session.getCurrentUserId());
        assertFalse(session.isLoggedIn());
    }

    @Test
    void testSetCurrentUserId() {
        session.setCurrentUserId("user-001");

        assertEquals("user-001", session.getCurrentUserId());
        assertTrue(session.isLoggedIn());
    }

    @Test
    void testClearSession() {
        session.setCurrentUserId("user-001");
        session.clearSession();

        assertNull(session.getCurrentUserId());
        assertFalse(session.isLoggedIn());
    }

    @Test
    void testSwitchUser() {
        session.setCurrentUserId("user-001");
        session.setCurrentUserId("user-002");

        assertEquals("user-002", session.getCurrentUserId());
        assertTrue(session.isLoggedIn());
    }

    @Test
    void testNullUserId() {
        assertThrows(IllegalArgumentException.class,
                () -> session.setCurrentUserId(null));

        assertFalse(session.isLoggedIn());
    }

    @Test
    void testEmptyUserId() {
        assertThrows(IllegalArgumentException.class,
                () -> session.setCurrentUserId(""));

        assertFalse(session.isLoggedIn());
    }

    @Test
    void testBlankUserId() {
        assertThrows(IllegalArgumentException.class,
                () -> session.setCurrentUserId("   "));

        assertFalse(session.isLoggedIn());
    }

    @Test
    void testClearInactiveSession() {
        session.clearSession();

        assertNull(session.getCurrentUserId());
        assertFalse(session.isLoggedIn());
    }
}
