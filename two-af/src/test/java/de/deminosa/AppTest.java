package de.deminosa;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeTrue;

import java.io.IOException;

import org.junit.Assume;
import org.junit.Test;

import com.google.zxing.WriterException;

import de.deminosa.utils.auth.AuthManager;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void generateSecretKey() {
        assertNotNull(new AuthManager().generateSecretKey());
    }

    @Test
    public void getGoogleAuthenticatorBarCode() {
        assertNotNull(new AuthManager()
        .getGoogleAuthenticatorBarCode("null", "Deminosa", "Minecraft 2FA"));
    }

    @Test
    public void createQRCode() {
        AuthManager manager = new AuthManager();
        String key = manager.generateSecretKey();
        String barcode = manager.getGoogleAuthenticatorBarCode(key, "Deminosa", "Minecraft 2FA");
        assertNotNull(manager.createQRCode(barcode, 300, 300));
    }
}
