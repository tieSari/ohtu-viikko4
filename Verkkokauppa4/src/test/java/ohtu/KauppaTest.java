package ohtu;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import ohtu.verkkokauppa.*;

/**
 *
 * @author sariraut
 */
public class KauppaTest {

    public KauppaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ostoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitteen 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo
        //1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(5));

    }
    //aloitetaan asiointi, koriin lisätään kaksi eri tuotetta, joita varastossa on ja suoritetaan ostos.
    //varmistettava
    //että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla

    @Test
    public void ostostenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitteen 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo
        //1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 4));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(9));

    }

    //aloitetaan asiointi, koriin lisätään kaksi samaa tuotetta jota on varastossa tarpeeksi 
    //ja suoritetaan ostos. varmistettava 
    //että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
    @Test
    public void kaksiSamaaOstostaTehtyPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitteen 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo
        //1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(10));

    }

    //aloitetaan asiointi, koriin lisätään tuote jota on varastossa tarpeeksi ja 
    //tuote joka on loppu ja suoritetaan ostos. varmistettava 
    //että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
    @Test
    public void ostostenOnJaEiJalkeenMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitteen 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo
        //1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(null);

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu oikeilla parametreilla
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(5));

    }
    //varmistettava, että metodin aloitaAsiointi kutsuminen nollaa edellisen ostoksen tiedot 
    //(eli edellisen ostoksen hinta ei näy uuden ostoksen hinnassa), katso tarvittaessa 
    //       apua projektin MockitoDemo testeistä!

    @Test
    public void aloitaAsiointiNollaaEdellisenOstoksen() {

        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
    }

    //varmistettava, että kauppa pyytää uuden viitenumeron jokaiselle
    //maksutapahtumalle, katso tarvittaessa apua projektin MockitoDemo testeistä!
    @Test
    public void uusiViiteJokaTapahtumalle() {
        Pankki mockPankki = mock(Pankki.class);
        Viitegeneraattori mockViite = mock(Viitegeneraattori.class);
        Varasto mockVarasto = mock(Varasto.class);
        when(mockViite.uusi()).
                thenReturn(1).
                thenReturn(2).
                thenReturn(3);

        when(mockVarasto.saldo(1)).thenReturn(10);
        when(mockVarasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        Kauppa k = new Kauppa(mockVarasto, mockPankki, mockViite);

        k.aloitaAsiointi();
        k.lisaaKoriin(1);

        k.tilimaksu("pekka", "12345");
        verify(mockPankki).tilisiirto(anyString(), eq(1), anyString(), anyString(), anyInt());
        k.tilimaksu("pekka", "12345");
        verify(mockPankki).tilisiirto(anyString(), eq(2), anyString(), anyString(), anyInt());
        k.tilimaksu("pekka", "12345");
        verify(mockPankki).tilisiirto(anyString(), eq(3), anyString(), anyString(), anyInt());

    }

    @Test
    public void poistaKoristaPoistaaOikein() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitteen 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.poistaKorista(1);
        verify(varasto).haeTuote(eq(1));

    }
}
