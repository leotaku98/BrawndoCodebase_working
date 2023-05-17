package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;
import java.util.Arrays;
import java.util.Objects;

public class ProductValueObject {
    private final String name;
    private final double[] manufacturingData;
    private final double cost;
    private final int recipeData;
    private final int marketingData;
    private final int safetyData;
    private final int licensingData;

    public ProductValueObject(String name,
                              double cost,
                              double[] manufacturingData,
                              int recipeData,
                              int marketingData,
                              int safetyData,
                              int licensingData) {
        this.name = name;
        this.cost = cost;
        this.manufacturingData = manufacturingData;
        this.recipeData = recipeData;
        this.marketingData = marketingData;
        this.safetyData = safetyData;
        this.licensingData = licensingData;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public double[] getManufacturingData() {
        return manufacturingData;
    }

    public int getRecipeData() {
        return recipeData;
    }

    public int getMarketingData() {
        return marketingData;
    }

    public int getSafetyData() {
        return safetyData;
    }

    public int getLicensingData() {
        return licensingData;
    }

    @Override
    public boolean equals(Object compare) {
        if (!(compare instanceof ProductValueObject) || compare == null) {
            return false;
        }
        ProductValueObject obj = (ProductValueObject) compare;
        if (obj.getCost() != this.cost){
            return false;
        }
        if (!Arrays.equals(obj.getManufacturingData(), this.manufacturingData)){
            return false;
        }
        if (obj.getRecipeData() != this.recipeData){
            return false;
        }
        if (obj.getMarketingData()!=this.marketingData){
            return false;
        }
        if (obj.getSafetyData() != this.safetyData){
            return false;
        }
        if (obj.getLicensingData() != this.licensingData){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, Arrays.hashCode(manufacturingData), recipeData, marketingData, safetyData, licensingData);
    }

}
