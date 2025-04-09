package learn.solar.domain;

import learn.solar.data.DataException;
import learn.solar.data.PanelRepositoryDouble;
import learn.solar.models.Material;
import learn.solar.models.Panel;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PanelServiceTest {

    PanelService service = new PanelService(new PanelRepositoryDouble());

    @Test
    void findBySection() throws DataException {
        List<Panel> actual = service.findBySection("Main");
        assertNotNull(actual);

        assertEquals(1, actual.get(0).getId());
        assertEquals("Main", actual.get(0).getSection());
        assertEquals(3, actual.get(0).getRow());
        assertEquals(4, actual.get(0).getColumn());
        assertEquals(2017, actual.get(0).getInstallationYear());
        assertEquals(Material.CDTE, actual.get(0).getMaterial());
        assertFalse(actual.get(0).isTracking());
    }

    @Test
    void shouldNotFindSection() throws DataException {
        List<Panel> actual = service.findBySection("Middle Hill");
        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldAdd() throws DataException {
        PanelResult actual = service.add(new Panel(0, "Main", 1, 1, 2017, Material.CIGS, true));
        assertNotNull(actual);
        assertTrue(actual.isSuccess());
        assertEquals(4, actual.getPanel().getId());
    }

    @Test
    void shouldNotAddDuplicatePanel() throws DataException {
        PanelResult should = service.add(new Panel(0, "Main", 1, 1, 2017, Material.CIGS, true));
        assertTrue(should.isSuccess());
        assertNotNull(should.getPanel());

        PanelResult actual = service.add(new Panel(4, "Main", 1, 1, 2023, Material.MONOSI, true));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Cannot create an existing panel.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddNullPanel() throws DataException {
        PanelResult actual = service.add(null);
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Panel cannot be null.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutSection() throws DataException {
        PanelResult actual = service.add(new Panel(4, "", 1, 1, 2017, Material.CIGS, true));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Section is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithNullSection() throws DataException {
        PanelResult actual = service.add(new Panel(4, null, 1, 1, 2017, Material.CIGS, true));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Section is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddNegativeRow() throws DataException {
        PanelResult actual = service.add(new Panel(4, "Main", -1, 1, 2017, Material.CIGS, true));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Row must be a positive value less than 250.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddRowGreaterThan250() throws DataException {
        PanelResult actual = service.add(new Panel(4, "Main", 256, 1, 2017, Material.CIGS, true));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Row must be a positive value less than 250.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddNegativeColumn() throws DataException {
        PanelResult actual = service.add(new Panel(4, "Main", 1, -1, 2017, Material.CIGS, true));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Column must be a positive value less than 250.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddColumnGreaterThan250() throws DataException {
        PanelResult actual = service.add(new Panel(4, "Main", 1, 345, 2017, Material.CIGS, true));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Column must be a positive value less than 250.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddInstallationYearInFuture() throws DataException {
        PanelResult actual = service.add(new Panel(4, "Main", 1, 1, 2034, Material.CIGS, true));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Installation Year must be a date before " + Year.now().getValue(), actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddWithoutMaterial() throws DataException {
        PanelResult actual = service.add(new Panel(4, "Main", 1, 1, 2017, null, true));
        assertFalse(actual.isSuccess());
        assertNull(actual.getPanel());
        assertEquals("Material is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldUpdate() throws DataException {
        Panel updated = service.findBySection("Main").get(0);
        updated.setRow(2);

        PanelResult actual = service.update(updated);
        assertTrue(actual.isSuccess());
        assertEquals(0, actual.getMessages().size());
        assertEquals(2, updated.getRow());
    }

    @Test
    void shouldNotUpdateNullPanel() throws DataException {
        PanelResult actual = service.update(null);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertTrue(actual.getMessages().get(0).contains("cannot be null"));
    }

    @Test
    void shouldNotUpdatePanelWithUnknownSection() throws DataException {
        Panel panel = new Panel(0, "Fake", 1, 1, 2017, Material.CIGS, true);
        PanelResult actual = service.update(panel);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
    }

    @Test
    void shouldNotUpdateUnknownPanel() throws DataException {
        Panel panel = new Panel(0, "Main", 1, 1, 2017, Material.CIGS, true);
        PanelResult actual = service.update(panel);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertTrue(actual.getMessages().get(0).contains("does not exist"));
    }

    @Test
    void shouldDeleteById() throws DataException {
        PanelResult actual = service.deleteById(1);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotDeleteByUnknownId() throws DataException {
        PanelResult actual = service.deleteById(9999);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertTrue(actual.getMessages().get(0).contains("does not exist"));
    }
}