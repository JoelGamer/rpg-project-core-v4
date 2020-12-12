package Controllers;

import Components.City.CitySize;
import Core.CoreDatabase;
import Exceptions.InvalidValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ControllerCitySize {

    private final CoreDatabase coreDatabase;

    public ControllerCitySize(CoreDatabase coreDatabase) {
        this.coreDatabase = coreDatabase;
    }

    public void getCitySize(CitySize citySize) throws InvalidValue, SQLException {
        String query = "SELECT uid, name, house_quantity_minimum, bank_quantity_minimum, shop_quantity_minimum " +
                "FROM city_size " +
                "WHERE uid=" + citySize.getUid();

        Statement statement = coreDatabase.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            citySize.setUid(resultSet.getLong(1));
            citySize.setName(resultSet.getString(2));
            citySize.setHouseQuantityMinimum(resultSet.getInt(3));
            citySize.setBankQuantityMinimum(resultSet.getInt(4));
            citySize.setShopQuantityMinimum(resultSet.getInt(5));
        }

        statement.close();
    }
}
