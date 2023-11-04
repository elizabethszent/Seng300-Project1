//Elizabeth Szentmiklossy UCID: 30165216
//Justine Mangaliman UCID: 30164741
//Enzo Mutiso UCID: 30182555
//Abdelrahman Mohamed UCID: 30162037
//Mohammad Mustafa Mehtab UCID: 30189394

package com.thelocalmarketplace.software;

import com.jjjwelectronics.IDevice;
import com.jjjwelectronics.IDeviceListener;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodeScannerListener;
import com.jjjwelectronics.scanner.IBarcodeScanner;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import java.util.ArrayList;

public final class AddItemByBarcode implements BarcodeScannerListener {

    private double expectedWeight;
    private final ArrayList<Product> order;
    private WeightDiscrepancy discrepancy;

    public AddItemByBarcode(double expectedWeight, ArrayList<Product> order, WeightDiscrepancy discrepancy) {
        this.expectedWeight = expectedWeight;
        this.order = order;
        this.discrepancy = discrepancy;
    }


    // Method to add item by barcode scan
    public void aBarcodeHasBeenScanned(IBarcodeScanner barcodeScanner, Barcode barcode) {
        // Check if state is satisfying the precondition: The system is ready to accept customer input.

        try {
            // Add gui to stop customer interaction
            System.out.println("Checking barcode...");

            Product product = getProductByBarcode(barcode);
            addBarcodedProductToOrder(product, order, barcodeScanner);

            // implement GUI saying to add to bagging area
            System.out.println("Item added.\nPlease add item to bagging area.\nWaiting...");
            // session simulation must implement logic to wait for item to be added to bagging area
            // then call getExpectedWeight from this and compare the actual vs expected weight

        } catch (ProductNotFoundException e) { // need to implement exceptions in session simulation I think?
            // GUI message would go here
            e.printStackTrace();
        }
    }

    // Method to get product information by barcode
    private BarcodedProduct getProductByBarcode(Barcode scannedBarcode) throws ProductNotFoundException {
        if (ProductDatabases.BARCODED_PRODUCT_DATABASE.containsKey(scannedBarcode)) {
            return ProductDatabases.BARCODED_PRODUCT_DATABASE.get(scannedBarcode);
        } else {
            throw new ProductNotFoundException("Product not found with specified barcode.");
        }
    }

    // so that simulation session can get the expected weight when comparing with scale
    public double getExpectedWeight( ) {
        return expectedWeight;
    }

    private void addBarcodedProductToOrder(Product product, ArrayList<Product> order, IBarcodeScanner barcodeScanner) {
        order.add(product);
        expectedWeight += ((BarcodedProduct) product).getExpectedWeight();
        
        if(discrepancy.CompareWeight()) {
        	barcodeScanner.enable();
        } else {
        	barcodeScanner.disable();
        }
        	
    }

    @Override
    public void aDeviceHasBeenEnabled(IDevice<? extends IDeviceListener> device) {
        // todo
        // Auto generated method stub
    }

    @Override
    public void aDeviceHasBeenDisabled(IDevice<? extends IDeviceListener> device) {
        // todo
        // Auto generated method stub
    }

    @Override
    public void aDeviceHasBeenTurnedOn(IDevice<? extends IDeviceListener> device) {
        // todo
        // Auto generated method stub
    }

    @Override
    public void aDeviceHasBeenTurnedOff(IDevice<? extends IDeviceListener> device) {
        // todo
        // Auto generated method stub
    }


}