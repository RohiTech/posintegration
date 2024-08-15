-- Sep 11, 2011 9:26:16 PM MYT
-- Export Queue Path Setting in Process
UPDATE AD_Process SET Help='You can select both categories. To erase, leave them blank.',Updated=TO_TIMESTAMP('2011-09-11 21:26:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=53271
;

-- Sep 11, 2011 9:26:16 PM MYT
-- Export Queue Path Setting in Process
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=53271
;

-- Sep 11, 2011 9:26:17 PM MYT
-- Export Queue Path Setting in Process
UPDATE AD_Menu SET Description='Export Products or Customers to Queue', IsActive='Y', Name='Export to Queue',Updated=TO_TIMESTAMP('2011-09-11 21:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53370
;

-- Sep 11, 2011 9:26:17 PM MYT
-- Export Queue Path Setting in Process
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=53370
;

-- Sep 11, 2011 9:26:17 PM MYT
-- Export Queue Path Setting in Process
UPDATE AD_WF_Node SET Help='You can select both categories. To erase, leave them blank.', Name='Export to Queue', Description='Export Products or Customers to Queue',Updated=TO_TIMESTAMP('2011-09-11 21:26:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50122
;

-- Sep 11, 2011 9:26:17 PM MYT
-- Export Queue Path Setting in Process
UPDATE AD_WF_Node_Trl SET IsTranslated='N' WHERE AD_WF_Node_ID=50122
;

-- Sep 11, 2011 9:31:00 PM MYT
-- Export Queue Path Setting in Process
UPDATE AD_Process_Para SET ColumnName='LinkProducts', DefaultValue='/queue/Products', Description='Set Queue PATH for either Products', EntityType='A', Help='POS'' openbravo.properties must also be same Products-Queue ', Name='Queue Path for Products',Updated=TO_TIMESTAMP('2011-09-11 21:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=53556
;

-- Sep 11, 2011 9:31:00 PM MYT
-- Export Queue Path Setting in Process
UPDATE AD_Process_Para_Trl SET IsTranslated='N' WHERE AD_Process_Para_ID=53556
;

-- Sep 11, 2011 9:33:41 PM MYT
-- Export Queue Path Setting in Process
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,3025,0,53271,53566,10,'LinkCustomers',TO_TIMESTAMP('2011-09-11 21:33:33','YYYY-MM-DD HH24:MI:SS'),100,'/queue/Customers','Set Queue Path for Customers at MQ','A',22,'POS'' openbravo.properties must be the same path for customers','Y','Y','N','N','Queue Path for Customers',25,TO_TIMESTAMP('2011-09-11 21:33:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 11, 2011 9:33:41 PM MYT
-- Export Queue Path Setting in Process
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53566 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

