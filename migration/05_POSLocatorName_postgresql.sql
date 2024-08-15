-- Sep 18, 2011 2:56:08 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,62203,469,0,10,591,'Name',TO_TIMESTAMP('2011-09-18 14:56:06','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','U',22,'The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','Y','N','N','N','N','N','N','N','Y','N','N','Y','Name',0,TO_TIMESTAMP('2011-09-18 14:56:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Sep 18, 2011 2:57:08 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,55300,0,'LocatorName',TO_TIMESTAMP('2011-09-18 14:57:07','YYYY-MM-DD HH24:MI:SS'),100,'A','Y','LocatorName','LocatorName',TO_TIMESTAMP('2011-09-18 14:57:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 18, 2011 2:57:08 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=55300 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Sep 18, 2011 2:57:22 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,62204,55300,0,10,591,'LocatorName',TO_TIMESTAMP('2011-09-18 14:57:22','YYYY-MM-DD HH24:MI:SS'),100,'U',22,'Y','Y','N','N','N','N','N','N','N','Y','N','N','Y','LocatorName',0,TO_TIMESTAMP('2011-09-18 14:57:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- Sep 18, 2011 2:57:22 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=62204 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Sep 18, 2011 5:15:34 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Column SET ColumnName='Name', Description='POS Locator Name', Help='Similar to Warehouse Name that contain the Locator and Storage that carries OnHandQty',Updated=TO_TIMESTAMP('2011-09-18 17:15:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=62204
;

-- Sep 18, 2011 5:15:34 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Field SET Name='LocatorName', Description='POS Locator Name', Help='Similar to Warehouse Name that contain the Locator and Storage that carries OnHandQty' WHERE AD_Column_ID=62204 AND IsCentrallyMaintained='Y'
;

-- Sep 18, 2011 5:17:52 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Column SET Description='POS Locator Name matching to Warehouse of Storage', Help='This POS Locator name is similar to Warehouse Name that owns the Locator and Storage of OnHandQty', Name='POS Locator Name',Updated=TO_TIMESTAMP('2011-09-18 17:17:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=62204
;

-- Sep 18, 2011 5:17:52 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=62204
;

-- Sep 18, 2011 5:17:52 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Field SET Name='POS Locator Name', Description='POS Locator Name matching to Warehouse of Storage', Help='This POS Locator name is similar to Warehouse Name that owns the Locator and Storage of OnHandQty' WHERE AD_Column_ID=62204 AND IsCentrallyMaintained='Y'
;

-- Sep 18, 2011 5:18:50 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Column SET ColumnName='PosLocatorName',Updated=TO_TIMESTAMP('2011-09-18 17:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=62204
;

-- Sep 18, 2011 5:22:28 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,Updated,UpdatedBy) VALUES (0,62204,62471,0,512,TO_TIMESTAMP('2011-09-18 17:22:26','YYYY-MM-DD HH24:MI:SS'),100,0,'U','Y','Y','Y','N','N','N','N','N','LocatorName',185,0,TO_TIMESTAMP('2011-09-18 17:22:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 18, 2011 5:22:28 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=62471 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Sep 18, 2011 5:22:50 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Field SET Name='POS Locator Name',Updated=TO_TIMESTAMP('2011-09-18 17:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=62471
;

-- Sep 18, 2011 5:22:50 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=62471
;

-- Sep 18, 2011 5:23:08 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Field SET DisplayLength=14,Updated=TO_TIMESTAMP('2011-09-18 17:23:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=62471
;

-- Sep 18, 2011 5:23:21 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2011-09-18 17:23:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=7346
;

-- Sep 18, 2011 5:23:25 PM MYT
-- POS ID Synch to WarehouseLocator in ImportOrder
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2011-09-18 17:23:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=7345
;

-- Sep 18, 2011 5:41:55 PM MYT
-- synch I_Order PosNameLocator with DB
ALTER TABLE I_Order ADD COLUMN PosLocatorName VARCHAR(22) DEFAULT NULL 
;

