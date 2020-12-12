package Components.City;

import Components.Component;
import Controllers.ControllerCitySize;
import Core.CoreDatabase;
import Exceptions.InvalidValue;
import java.sql.SQLException;

public class CitySize extends Component {

    private String name;
    private int houseQuantityMinimum;
    private int bankQuantityMinimum;
    private int shopQuantityMinimum;

    public CitySize(long uid) throws InvalidValue {
        super(uid);
    }

    public CitySize retrieveCitySize(CoreDatabase coreDatabase) throws InvalidValue, SQLException {
        new ControllerCitySize(coreDatabase).getCitySize(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHouseQuantityMinimum() {
        return houseQuantityMinimum;
    }

    public void setHouseQuantityMinimum(int houseQuantityMinimum) {
        this.houseQuantityMinimum = houseQuantityMinimum;
    }

    public int getBankQuantityMinimum() {
        return bankQuantityMinimum;
    }

    public void setBankQuantityMinimum(int bankQuantityMinimum) {
        this.bankQuantityMinimum = bankQuantityMinimum;
    }

    public int getShopQuantityMinimum() {
        return shopQuantityMinimum;
    }

    public void setShopQuantityMinimum(int shopQuantityMinimum) {
        this.shopQuantityMinimum = shopQuantityMinimum;
    }
}
