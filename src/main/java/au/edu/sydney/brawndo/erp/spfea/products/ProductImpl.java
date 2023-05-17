package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Objects;

public class ProductImpl implements Product {

    private final String name;
    private final double[] manufacturingData;
    private final double cost;
    private Integer recipeDataKey;
    private Integer marketingDataKey;
    private Integer safetyDataKey;
    private Integer licensingDataKey;
    private final FlyweightFactory flyweightFac;
    private final ProductValueObject valueObject;

    public ProductImpl(String name,
                       double cost,
                       double[] manufacturingData,
                       double[] recipeData,
                       double[] marketingData,
                       double[] safetyData,
                       double[] licensingData) {
        flyweightFac = FlyweightFactory.getInstance();
        this.name = name;
        this.cost = cost;
        this.manufacturingData = manufacturingData;
        this.recipeDataKey = flyweightFac.addFlyweight(recipeData);
        this.marketingDataKey = flyweightFac.addFlyweight(marketingData);
        this.safetyDataKey = flyweightFac.addFlyweight(safetyData);
        this.licensingDataKey = flyweightFac.addFlyweight(licensingData);
        valueObject = new ProductValueObject(name, cost, manufacturingData, recipeDataKey, marketingDataKey, safetyDataKey, licensingDataKey);

    }

    @Override
    public String getProductName() {
        return name;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double[] getManufacturingData() {
        return manufacturingData;
    }

    @Override
    public double[] getRecipeData() {
        return flyweightFac.getData(recipeDataKey);
    }

    @Override
    public double[] getMarketingData() {
        return flyweightFac.getData(marketingDataKey);
    }

    @Override
    public double[] getSafetyData() {
        return flyweightFac.getData(safetyDataKey);
    }

    @Override
    public double[] getLicensingData() {
        return flyweightFac.getData(licensingDataKey);
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }

    public ProductValueObject getValueObject(){
        return this.valueObject;
    }

    @Override
    public boolean equals(Object compare) {
        if (!(compare instanceof ProductImpl) || compare == null) {
            return false;
        }
        ProductImpl product = (ProductImpl) compare;
        return Objects.equals(valueObject, product.getValueObject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueObject);
    }
}
