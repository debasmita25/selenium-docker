package com.debs.pages.vendorapp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.debs.utils.BasePage;

public class DashboardPage extends BasePage {
	
	@FindBy(id = "monthly-earning")
    private WebElement monthlyEarningElement;

    @FindBy(id = "annual-earning")
    private WebElement annualEarningElement;

    @FindBy(id = "profit-margin")
    private WebElement profitMarginElement;

    @FindBy(id = "available-inventory")
    private WebElement availableInventoryElement;

    @FindBy(css = "#dataTable_filter input")
    private WebElement searchInput;

    @FindBy(id = "dataTable_info")
    private WebElement searchResultsCountElement;

    @FindBy(css = "img.img-profile")
    private WebElement userProfilePictureElement;

    // prefer id / name / css
    @FindBy(linkText = "Logout")
    private WebElement logoutLink;

    @FindBy(css = "#logoutModal a")
    private WebElement modalLogoutButton;
	
	public DashboardPage()
	{
		super();
	}

	@Override
	public boolean verifyElement() {
		// TODO Auto-generated method stub
		return isDisplayed_Enabled(userProfilePictureElement);
	}
	
	public String getMonthlyEarning(){
        return getText(monthlyEarningElement);
    }

    public String getAnnualEarning(){
    	return getText(annualEarningElement);
    }

    public String getProfitMargin(){
        return getText(profitMarginElement);
    }

    public String getAvailableInventory(){
        return getText(availableInventoryElement);
    }

    public void searchOrderHistoryBy(String keyword){
    	sendKeys(searchInput, keyword);
        
    }

    /*
        Showing 1 to 10 of 32 entries (filtered from 99 total entries)
        arr[0] = "Showing"
        arr[1] = "1"
        arr[2] = "to"
        arr[3] = "10"
        arr[4] = "of"
        arr[5] = "32"
        ...
        ...
     */
    public int getSearchResultsCount(){
        String resultsText = this.searchResultsCountElement.getText();
        String[] arr = resultsText.split(" ");
        // if we do not have 5th item, it would throw exception.
        // that's fine. we would want our test to fail anyway in that case!
        int count = Integer.parseInt(arr[5]);
        return count;
    }

    public void logout(){
    	click(userProfilePictureElement);
    	click(logoutLink);
    	click(modalLogoutButton);
        
    }
	

}
