import br.ce.wcaquino.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class AssertTest {


    @Test
    public void teste() {
        Assert.assertTrue(true);
        Assert.assertFalse(false);

        Assert.assertEquals("Erro de comparação!",1, 1); // Mensage, experado, atual
        Assert.assertEquals(0.51234, 0.512, 0.001); // o delta 0.01 é usando como margem de erro
        Assert.assertEquals(Math.PI, 3.14, 0.01);

        int i = 5;
        Integer i2 = 5;
        Assert.assertEquals(Integer.valueOf(i), i2);
        Assert.assertEquals(i, i2.intValue());

        Assert.assertEquals("bola", "bola");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertTrue("bola".startsWith("bo"));
        Assert.assertNotEquals("bola", "casa");

        Usuario us1 = new Usuario("Usuario 1");
        Usuario us2 = new Usuario("Usuario 1");
        Usuario us3 = us2;
        Usuario us4 = null;


        Assert.assertEquals(us1, us2);
        Assert.assertSame(us2, us2);
        Assert.assertSame(us2, us3);
        Assert.assertNotSame(us1, us2);

        Assert.assertNull(us4);
        Assert.assertNotNull(us1);
    }

}
