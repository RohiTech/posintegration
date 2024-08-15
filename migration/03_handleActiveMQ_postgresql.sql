-- Sep 6, 2011 10:02:48 AM MYT
-- expand Export2Queue for Customers
UPDATE AD_Process SET Help='You can select both categories. To erase, leave them blank.',Updated=TO_TIMESTAMP('2011-09-06 10:02:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=53271
;

-- Sep 6, 2011 10:02:48 AM MYT
-- expand Export2Queue for Customers
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=53271
;

-- Sep 6, 2011 10:02:48 AM MYT
-- expand Export2Queue for Customers
UPDATE AD_Menu SET Description='Export Products or Customers to Queue', IsActive='Y', Name='Export to Queue',Updated=TO_TIMESTAMP('2011-09-06 10:02:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53370
;

-- Sep 6, 2011 10:02:48 AM MYT
-- expand Export2Queue for Customers
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=53370
;

-- Sep 6, 2011 10:02:48 AM MYT
-- expand Export2Queue for Customers
UPDATE AD_WF_Node SET Description='Export Products or Customers to Queue', Help='You can select both categories. To erase, leave them blank.', Name='Export to Queue',Updated=TO_TIMESTAMP('2011-09-06 10:02:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50122
;

-- Sep 6, 2011 10:02:48 AM MYT
-- expand Export2Queue for Customers
UPDATE AD_WF_Node_Trl SET IsTranslated='N' WHERE AD_WF_Node_ID=50122
;

