-- Sep 4, 2011 9:22:21 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53367,0,TO_TIMESTAMP('2011-09-04 09:22:20','YYYY-MM-DD HH24:MI:SS'),100,'Menu tree for POS items','A','Y','Y','N','N','Y','Point Of Sales (POS)',TO_TIMESTAMP('2011-09-04 09:22:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:22:21 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53367 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:22:21 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53367, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53367)
;

-- Sep 4, 2011 9:23:22 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53368,0,TO_TIMESTAMP('2011-09-04 09:23:21','YYYY-MM-DD HH24:MI:SS'),100,'legacy POS from Compiere, OpenXpertya and Adaxa','U','Y','Y','N','N','Y','Java POS',TO_TIMESTAMP('2011-09-04 09:23:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:23:22 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53368 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:23:22 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53368, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53368)
;

-- Sep 4, 2011 9:23:43 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=419
;

-- Sep 4, 2011 9:23:48 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=491
;

-- Sep 4, 2011 9:23:48 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=419
;

-- Sep 4, 2011 9:23:54 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53269
;

-- Sep 4, 2011 9:23:54 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=491
;

-- Sep 4, 2011 9:23:54 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=2, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=419
;

-- Sep 4, 2011 9:23:58 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=492
;

-- Sep 4, 2011 9:23:58 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53269
;

-- Sep 4, 2011 9:23:58 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=2, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=491
;

-- Sep 4, 2011 9:23:58 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=3, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=419
;

-- Sep 4, 2011 9:24:07 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=492
;

-- Sep 4, 2011 9:24:07 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53269
;

-- Sep 4, 2011 9:24:07 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=2, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=491
;

-- Sep 4, 2011 9:24:07 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53368, SeqNo=3, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=419
;

-- Sep 4, 2011 9:24:07 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53367, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53368
;

-- Sep 4, 2011 9:27:56 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53369,0,TO_TIMESTAMP('2011-09-04 09:27:55','YYYY-MM-DD HH24:MI:SS'),100,'Remote POS Synchronisation via ActiveMQ','A','Y','Y','N','N','Y','External POS Synchronisation',TO_TIMESTAMP('2011-09-04 09:27:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:27:56 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53369 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:27:56 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53369, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53369)
;

-- Sep 4, 2011 9:28:00 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53367, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53369
;

-- Sep 4, 2011 9:28:00 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53367, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53368
;

-- Sep 4, 2011 9:29:38 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,"action",Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53370,0,'P',TO_TIMESTAMP('2011-09-04 09:29:37','YYYY-MM-DD HH24:MI:SS'),100,'Export ERP Products to ActiveMQ URL','A','Y','Y','N','N','N','Export Products to Queue',TO_TIMESTAMP('2011-09-04 09:29:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:29:38 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53370 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:29:38 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53370, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53370)
;

-- Sep 4, 2011 9:30:43 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,"action",Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53371,0,'P',TO_TIMESTAMP('2011-09-04 09:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Import external POS sales orders from ActiveMQ URL','A','Y','Y','N','N','N','Import  Orders from Queue',TO_TIMESTAMP('2011-09-04 09:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:30:43 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53371 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:30:43 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53371, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53371)
;

-- Sep 4, 2011 9:32:05 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,"action",Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53372,0,281,'W',TO_TIMESTAMP('2011-09-04 09:32:04','YYYY-MM-DD HH24:MI:SS'),100,'Process Imported Orders (Import Order window)','A','Y','Y','N','N','N','Process Imported Orders',TO_TIMESTAMP('2011-09-04 09:32:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:32:05 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53372 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:32:05 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53372, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53372)
;

-- Sep 4, 2011 9:35:16 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,"action",Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53373,0,'T',TO_TIMESTAMP('2011-09-04 09:35:15','YYYY-MM-DD HH24:MI:SS'),100,'Activate or RUN the ActiveMQ service on server','U','Y','Y','N','N','N','Turn Queue ON',TO_TIMESTAMP('2011-09-04 09:35:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:35:16 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53373 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:35:16 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53373, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53373)
;

-- Sep 4, 2011 9:36:10 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,"action",Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53374,0,'T',TO_TIMESTAMP('2011-09-04 09:36:09','YYYY-MM-DD HH24:MI:SS'),100,'Turn of the ActiveMQ service on the server','A','Y','Y','N','N','N','Turn Queue OFF',TO_TIMESTAMP('2011-09-04 09:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:36:10 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53374 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:36:10 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53374, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53374)
;

-- Sep 4, 2011 9:37:40 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,"action",Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53375,0,'T',TO_TIMESTAMP('2011-09-04 09:37:39','YYYY-MM-DD HH24:MI:SS'),100,'remove the XML queue from ActiveMQ service after all POSes already read it','A','Y','Y','N','N','N','Flush Products Queue',TO_TIMESTAMP('2011-09-04 09:37:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:37:40 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53375 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:37:40 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53375, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53375)
;

-- Sep 4, 2011 9:39:18 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu (AD_Client_ID,AD_Menu_ID,AD_Org_ID,"action",Created,CreatedBy,Description,EntityType,IsActive,IsCentrallyMaintained,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,53376,0,'F',TO_TIMESTAMP('2011-09-04 09:39:17','YYYY-MM-DD HH24:MI:SS'),100,'Workflow to give proper to items in menu','A','Y','Y','N','N','N','Synchronise POS Workflow',TO_TIMESTAMP('2011-09-04 09:39:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 9:39:18 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=53376 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- Sep 4, 2011 9:39:18 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID, 0, 'Y', CURRENT_TIMESTAMP, 100, CURRENT_TIMESTAMP, 100,t.AD_Tree_ID, 53376, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.TreeType='MM' AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=53376)
;

-- Sep 4, 2011 9:39:33 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53375
;

-- Sep 4, 2011 9:39:36 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53374
;

-- Sep 4, 2011 9:39:36 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53375
;

-- Sep 4, 2011 9:40:09 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53372
;

-- Sep 4, 2011 9:40:09 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53374
;

-- Sep 4, 2011 9:40:09 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=2, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53375
;

-- Sep 4, 2011 9:40:29 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53371
;

-- Sep 4, 2011 9:40:29 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53372
;

-- Sep 4, 2011 9:40:29 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=2, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53374
;

-- Sep 4, 2011 9:40:29 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=3, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53375
;

-- Sep 4, 2011 9:40:37 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53370
;

-- Sep 4, 2011 9:40:37 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53371
;

-- Sep 4, 2011 9:40:37 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=2, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53372
;

-- Sep 4, 2011 9:40:37 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=3, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53374
;

-- Sep 4, 2011 9:40:37 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=4, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53375
;

-- Sep 4, 2011 9:40:41 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53373
;

-- Sep 4, 2011 9:40:41 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53370
;

-- Sep 4, 2011 9:40:41 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=2, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53371
;

-- Sep 4, 2011 9:40:41 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=3, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53372
;

-- Sep 4, 2011 9:40:41 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=4, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53374
;

-- Sep 4, 2011 9:40:41 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=5, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53375
;

-- Sep 4, 2011 9:40:46 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=0, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53376
;

-- Sep 4, 2011 9:40:46 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=1, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53373
;

-- Sep 4, 2011 9:40:46 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=2, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53370
;

-- Sep 4, 2011 9:40:46 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=3, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53371
;

-- Sep 4, 2011 9:40:46 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=4, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53372
;

-- Sep 4, 2011 9:40:46 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=5, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53374
;

-- Sep 4, 2011 9:40:46 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_TreeNodeMM SET Parent_ID=53369, SeqNo=6, Updated=CURRENT_TIMESTAMP WHERE AD_Tree_ID=10 AND Node_ID=53375
;

-- Sep 4, 2011 10:22:53 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Workflow (AD_Client_ID,AD_Org_ID,AD_Workflow_ID,AccessLevel,Author,Cost,Created,CreatedBy,DocumentNo,Duration,DurationUnit,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,"limit",Name,Priority,PublishStatus,Updated,UpdatedBy,ValidateWorkflow,Value,Version,WaitingTime,WorkflowType,WorkingTime) VALUES (0,0,50025,'7','red1/Sysnova',0,TO_TIMESTAMP('2011-09-04 10:22:51','YYYY-MM-DD HH24:MI:SS'),100,'10000000',0,'D','A','Y','N','N','N',0,'Guide to operate the External POS Workflow',0,'U',TO_TIMESTAMP('2011-09-04 10:22:51','YYYY-MM-DD HH24:MI:SS'),100,'N','POS Synchronisation Workflow',1,0,'G',0)
;

-- Sep 4, 2011 10:22:53 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Workflow_ID=50025 AND NOT EXISTS (SELECT * FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- Sep 4, 2011 10:25:29 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu SET AD_Workflow_ID=105,Updated=TO_TIMESTAMP('2011-09-04 10:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53376
;

-- Sep 4, 2011 10:32:26 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Task (AD_Client_ID,AD_Org_ID,AD_Task_ID,AccessLevel,Created,CreatedBy,Description,EntityType,IsActive,IsServerProcess,Name,OS_Command,Updated,UpdatedBy) VALUES (0,0,50000,'7',TO_TIMESTAMP('2011-09-04 10:32:26','YYYY-MM-DD HH24:MI:SS'),100,'Turns on the ActiveMQ service','A','Y','N','Turn Queue ON','@ADEMPIERE_HOME@@/@utils@/@activemq@/@activemq console@',TO_TIMESTAMP('2011-09-04 10:32:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 10:32:27 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Task_Trl (AD_Language,AD_Task_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Task_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Task t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Task_ID=50000 AND NOT EXISTS (SELECT * FROM AD_Task_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Task_ID=t.AD_Task_ID)
;

-- Sep 4, 2011 10:54:05 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Task (AD_Client_ID,AD_Org_ID,AD_Task_ID,AccessLevel,Created,CreatedBy,Description,EntityType,Help,IsActive,IsServerProcess,Name,OS_Command,Updated,UpdatedBy) VALUES (0,0,50001,'7',TO_TIMESTAMP('2011-09-04 10:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Turn ActiveMQ service OFF','U','Stopping the activeMQ service may also flush the XML queue','Y','N','Turn Queue OFF','@ADEMPIERE_HOME@@/@utils@/@activemq@/@activemq stop@',TO_TIMESTAMP('2011-09-04 10:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 10:54:05 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Task_Trl (AD_Language,AD_Task_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Task_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Task t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Task_ID=50001 AND NOT EXISTS (SELECT * FROM AD_Task_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Task_ID=t.AD_Task_ID)
;

-- Sep 4, 2011 10:55:38 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Task SET OS_Command='@ADEMPIERE_HOME@@/@utils@/@activemq@/@activemq start@',Updated=TO_TIMESTAMP('2011-09-04 10:55:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Task_ID=50000
;

-- Sep 4, 2011 10:59:14 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu SET AD_Task_ID=50000,Updated=TO_TIMESTAMP('2011-09-04 10:59:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53373
;

-- Sep 4, 2011 10:59:22 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu SET AD_Task_ID=50001,Updated=TO_TIMESTAMP('2011-09-04 10:59:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53374
;

-- Sep 4, 2011 10:59:42 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu SET "action"='P',Updated=TO_TIMESTAMP('2011-09-04 10:59:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53375
;

-- Sep 4, 2011 11:00:02 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_Node (AD_Client_ID,AD_Org_ID,AD_Task_ID,AD_WF_Node_ID,AD_Workflow_ID,"action",Cost,Created,CreatedBy,Description,DocAction,Duration,DynPriorityChange,EntityType,Help,IsActive,IsCentrallyMaintained,JoinElement,"limit",Name,Priority,SplitElement,Updated,UpdatedBy,Value,WaitTime,WaitingTime,WorkingTime,XPosition,YPosition) VALUES (0,0,50000,50120,50025,'T',0,TO_TIMESTAMP('2011-09-04 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Turn ON the ActiveMQ service on the server','CO',0,0,'A','runs activemq console on server','Y','Y','X',0,'Turn Queue ON',0,'X',TO_TIMESTAMP('2011-09-04 11:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Turn Queue ON',0,0,0,0,0)
;

-- Sep 4, 2011 11:00:02 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=50120 AND NOT EXISTS (SELECT * FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- Sep 4, 2011 11:01:17 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_Node (AD_Client_ID,AD_Org_ID,AD_Task_ID,AD_WF_Node_ID,AD_Workflow_ID,"action",Cost,Created,CreatedBy,Description,DocAction,Duration,DynPriorityChange,EntityType,Help,IsActive,IsCentrallyMaintained,JoinElement,"limit",Name,Priority,SplitElement,Updated,UpdatedBy,Value,WaitTime,WaitingTime,WorkingTime,XPosition,YPosition) VALUES (0,0,50001,50121,50025,'T',0,TO_TIMESTAMP('2011-09-04 11:01:16','YYYY-MM-DD HH24:MI:SS'),100,'Kills the ActiveMQ service','CO',0,0,'A','This will also flush out and end all content queue. To make queues persist even after shutting down, set this in the activemq properties.','Y','Y','X',0,'Turn Queue OFF',0,'X',TO_TIMESTAMP('2011-09-04 11:01:16','YYYY-MM-DD HH24:MI:SS'),100,'Turn Queue OFF',0,0,0,0,0)
;

-- Sep 4, 2011 11:01:17 AM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=50121 AND NOT EXISTS (SELECT * FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- Sep 4, 2011 11:07:01 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Workflow SET Description='Guide to operate the External POS Workflow', IsValid='N', Name='Syncrhonise POS Workflow',Updated=TO_TIMESTAMP('2011-09-04 11:07:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=50025
;

-- Sep 4, 2011 11:07:01 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Workflow_Trl SET IsTranslated='N' WHERE AD_Workflow_ID=50025
;

-- Sep 4, 2011 11:07:13 AM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu SET AD_Workflow_ID=50025,Updated=TO_TIMESTAMP('2011-09-04 11:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53376
;

-- Sep 4, 2011 12:53:33 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AccessLevel,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDirectPrint,IsReport,IsServerProcess,Name,ShowHelp,Statistic_Count,Statistic_Seconds,Updated,UpdatedBy,Value) VALUES (0,0,53271,'7','org.idempiere.process.Export2Queue','N',TO_TIMESTAMP('2011-09-04 12:53:31','YYYY-MM-DD HH24:MI:SS'),100,'Export Products or Customers to Queue','A','Select only one category. Leave the other field blank. This process does not modify anything within your ERP. It only writes to a activeMQ for the POS clients to pick up later.','Y','N','N','N','N','Export to Queue','Y',0,0,TO_TIMESTAMP('2011-09-04 12:53:31','YYYY-MM-DD HH24:MI:SS'),100,'Export to Queue')
;

-- Sep 4, 2011 12:53:33 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=53271 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Sep 4, 2011 12:57:48 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,3027,0,53271,53555,10,'LinkURL',TO_TIMESTAMP('2011-09-04 12:57:46','YYYY-MM-DD HH24:MI:SS'),100,'localhost','Set URL Host i.e. ''localhost''','A',0,'localhost or the full remote URI name. Port is already hard set in code as 61613. The ActiveMQ service at that host should be up and running. ','Y','Y','N','N','Set URL Host',10,TO_TIMESTAMP('2011-09-04 12:57:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 12:57:48 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53555 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 4, 2011 1:08:19 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,3025,0,53271,53556,10,'Link',TO_TIMESTAMP('2011-09-04 13:08:16','YYYY-MM-DD HH24:MI:SS'),100,'/queue/test','Set Queue PATH for either Products or Customers','U',22,'Counterpart at POS'' openbravo.properties should also be same Products-Queue or Customers-Queue.','Y','Y','N','N','Set Queue Path',20,TO_TIMESTAMP('2011-09-04 13:08:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 1:08:20 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53556 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 4, 2011 1:10:24 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,53271,53557,20,'IsSelfService',TO_TIMESTAMP('2011-09-04 13:10:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','SelfService flag for Products','U',0,'Set to N if you want to publish products listed as N in IsSelfService checkbox.','Y','Y','N','N','Self Service',30,TO_TIMESTAMP('2011-09-04 13:10:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 1:10:24 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53557 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 4, 2011 1:11:59 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,453,0,53271,53558,19,'M_Product_Category_ID',TO_TIMESTAMP('2011-09-04 13:11:58','YYYY-MM-DD HH24:MI:SS'),100,'Set Products Category','U',22,'Leave blank if you want to set Customers Category for exporting customers.','Y','Y','N','N','Products Category',40,TO_TIMESTAMP('2011-09-04 13:11:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 1:11:59 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53558 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 4, 2011 1:13:01 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1383,0,53271,53559,19,'C_BP_Group_ID',TO_TIMESTAMP('2011-09-04 13:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Set Customers Category','U',22,'Leave the Products Category blank if you wish to export customers info','Y','Y','N','N','Customers Category',50,TO_TIMESTAMP('2011-09-04 13:12:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 1:13:01 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53559 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 4, 2011 1:14:15 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,450,0,53271,53560,19,'M_PriceList_Version_ID',TO_TIMESTAMP('2011-09-04 13:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Set Price List Version','U',22,'Needed when exporting Products or pricing information will be blank','Y','Y','N','N','Price List',60,TO_TIMESTAMP('2011-09-04 13:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 1:14:15 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53560 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 4, 2011 1:14:39 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_Node (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_WF_Node_ID,AD_Workflow_ID,"action",Cost,Created,CreatedBy,Description,DocAction,Duration,DynPriorityChange,EntityType,Help,IsActive,IsCentrallyMaintained,JoinElement,"limit",Name,Priority,SplitElement,Updated,UpdatedBy,Value,WaitTime,WaitingTime,WorkingTime,XPosition,YPosition) VALUES (0,0,53271,50122,50025,'P',0,TO_TIMESTAMP('2011-09-04 13:14:38','YYYY-MM-DD HH24:MI:SS'),100,'Export Product or Customer Category items to ActiveMQ service','CO',0,0,'A','Select the category of Products to export. If you want to select Customers, then leave Product Category blank.','Y','Y','X',0,'Export Products or Customers to Queue',0,'X',TO_TIMESTAMP('2011-09-04 13:14:38','YYYY-MM-DD HH24:MI:SS'),100,'Export to Queue',0,0,0,0,0)
;

-- Sep 4, 2011 1:14:39 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=50122 AND NOT EXISTS (SELECT * FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- Sep 4, 2011 1:15:45 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu SET AD_Process_ID=53271, Description='Export ERP Products or Customers info to ActiveMQ URL', Name='Export Data to Queue',Updated=TO_TIMESTAMP('2011-09-04 13:15:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53370
;

-- Sep 4, 2011 1:15:45 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=53370
;

-- Sep 4, 2011 1:51:19 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AccessLevel,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDirectPrint,IsReport,IsServerProcess,Name,ShowHelp,Statistic_Count,Statistic_Seconds,Updated,UpdatedBy,Value) VALUES (0,0,53272,'7','org.idempiere.process.ImportQueue2AD','N',TO_TIMESTAMP('2011-09-04 13:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Import Sales Orders exported by openbravo POS and write to I_Order table','U','This process will read from the XML message stored in the ActiveMQ service. Ensure that the service is running. You can test it by checking the Test Run flag which will not flush the queue after reading. It will stored each record read in I_Order table for you to review or process.','Y','N','N','N','N','Import Orders from Queue','Y',0,0,TO_TIMESTAMP('2011-09-04 13:51:18','YYYY-MM-DD HH24:MI:SS'),100,'Import Orders from Queue')
;

-- Sep 4, 2011 1:51:19 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=53272 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Sep 4, 2011 1:53:54 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,2153,0,53272,53561,20,'Test',TO_TIMESTAMP('2011-09-04 13:53:53','YYYY-MM-DD HH24:MI:SS'),100,'N','If Yes will not remove record after reading','U',1,'Usually Orders are removed from queue after reading. But if you want to just review the Import Order table and then import again the same queue, then tick this box. If you wish to read again and have the queue removed then do not tick this box. You may need to delete all imported records if you are importing again to avoid conflict.','Y','Y','N','N','Test Run',10,TO_TIMESTAMP('2011-09-04 13:53:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 1:53:54 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53561 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 4, 2011 1:55:49 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu SET AD_Process_ID=53272,Updated=TO_TIMESTAMP('2011-09-04 13:55:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53371
;

-- Sep 4, 2011 2:05:37 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_Node (AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,"action",Cost,Created,CreatedBy,Description,DocAction,Duration,DynPriorityChange,EntityType,Help,IsActive,IsCentrallyMaintained,JoinElement,"limit",Name,Priority,SplitElement,Updated,UpdatedBy,Value,WaitTime,WaitingTime,WorkingTime,XPosition,YPosition) VALUES (0,0,50123,50025,'Z',0,TO_TIMESTAMP('2011-09-04 14:05:36','YYYY-MM-DD HH24:MI:SS'),100,'Import Orders from ActiveMQ which the openbravo POS client has exported to','CO',0,0,'A','The openbravo POS clients will do Orders Synchronise and at the end of the day the ERP will now run this to pick them all up. You can flag the checkbox to be Y if you want to do a test run on a test server to process the orders. If things are ok, then you uncheck the Test Run to do a real read onto a production server. The queue will be consumed and removed.','Y','Y','X',0,'Import Orders from External POS',0,'X',TO_TIMESTAMP('2011-09-04 14:05:36','YYYY-MM-DD HH24:MI:SS'),100,'Import Orders from External POS',0,0,0,0,0)
;

-- Sep 4, 2011 2:05:38 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_WF_Node_ID=50123 AND NOT EXISTS (SELECT * FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- Sep 4, 2011 2:06:08 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_WF_Node SET AD_Process_ID=53272, "action"='P',Updated=TO_TIMESTAMP('2011-09-04 14:06:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50123
;

-- Sep 4, 2011 2:07:11 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Workflow SET IsValid='N', Name='Synchronise POS Workflow',Updated=TO_TIMESTAMP('2011-09-04 14:07:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=50025
;

-- Sep 4, 2011 2:07:11 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Workflow_Trl SET IsTranslated='N' WHERE AD_Workflow_ID=50025
;

-- Sep 4, 2011 2:07:11 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu SET Description='Guide to operate the External POS Workflow', IsActive='Y', Name='Synchronise POS Workflow',Updated=TO_TIMESTAMP('2011-09-04 14:07:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53376
;

-- Sep 4, 2011 2:07:11 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=53376
;

-- Sep 4, 2011 2:07:48 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_NodeNext_ID,AD_WF_Node_ID,Created,CreatedBy,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,50122,50091,50120,TO_TIMESTAMP('2011-09-04 14:07:47','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',10,TO_TIMESTAMP('2011-09-04 14:07:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 2:07:57 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_NodeNext_ID,AD_WF_Node_ID,Created,CreatedBy,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,50123,50092,50122,TO_TIMESTAMP('2011-09-04 14:07:56','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',10,TO_TIMESTAMP('2011-09-04 14:07:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 2:08:26 PM MYT
-- ActiveMQ Synchronisation
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_NodeNext_ID,AD_WF_Node_ID,Created,CreatedBy,EntityType,IsActive,IsStdUserWorkflow,SeqNo,Updated,UpdatedBy) VALUES (0,0,50121,50093,50123,TO_TIMESTAMP('2011-09-04 14:08:25','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N',10,TO_TIMESTAMP('2011-09-04 14:08:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 2:08:29 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_WF_Node SET XPosition=5, YPosition=5,Updated=TO_TIMESTAMP('2011-09-04 14:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50120
;

-- Sep 4, 2011 2:08:29 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_WF_Node SET XPosition=230, YPosition=5,Updated=TO_TIMESTAMP('2011-09-04 14:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50121
;

-- Sep 4, 2011 2:08:29 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_WF_Node SET XPosition=5, YPosition=105,Updated=TO_TIMESTAMP('2011-09-04 14:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50122
;

-- Sep 4, 2011 2:08:29 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_WF_Node SET XPosition=230, YPosition=105,Updated=TO_TIMESTAMP('2011-09-04 14:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_Node_ID=50123
;

-- Sep 4, 2011 2:11:12 PM MYT
-- ActiveMQ Synchronisation
UPDATE AD_Workflow SET IsValid='N', PublishStatus='R',Updated=TO_TIMESTAMP('2011-09-04 14:11:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=50025
;

-- Sep 4, 2011 4:52:42 PM MYT
-- set Import2queue own params
UPDATE AD_Process_Para SET EntityType='A',Updated=TO_TIMESTAMP('2011-09-04 16:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=53561
;

-- Sep 4, 2011 4:52:53 PM MYT
-- set Import2queue own params
UPDATE AD_Process SET EntityType='A',Updated=TO_TIMESTAMP('2011-09-04 16:52:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=53272
;

-- Sep 4, 2011 4:53:37 PM MYT
-- set Import2queue own params
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,3027,0,53272,53562,10,'LinkURL',TO_TIMESTAMP('2011-09-04 16:53:35','YYYY-MM-DD HH24:MI:SS'),100,'Set URL Host i.e. ''localhost''','A',0,'localhost or the full remote URI name. Port is already hard set in code as 61613. The ActiveMQ service at that host should be up and running. ','Y','Y','N','N','Set URL Host',20,TO_TIMESTAMP('2011-09-04 16:53:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 4:53:37 PM MYT
-- set Import2queue own params
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53562 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 4, 2011 4:55:18 PM MYT
-- set Import2queue own params
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsCentrallyMaintained,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,3025,0,53272,53563,10,'Link',TO_TIMESTAMP('2011-09-04 16:55:17','YYYY-MM-DD HH24:MI:SS'),100,'Set Queue PATH for different data model','A',0,'Counterpart at POS'' openbravo.properties should also be same Orfers-Queue or others defined','Y','Y','N','N','Set Queue Path',30,TO_TIMESTAMP('2011-09-04 16:55:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Sep 4, 2011 4:55:18 PM MYT
-- set Import2queue own params
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=53563 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Sep 4, 2011 5:04:57 PM MYT
-- set Import2queue own params
UPDATE AD_Process_Para SET DefaultValue='localhost',Updated=TO_TIMESTAMP('2011-09-04 17:04:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=53562
;

-- Sep 4, 2011 5:05:05 PM MYT
-- set Import2queue own params
UPDATE AD_Process_Para SET DefaultValue='/queue/test',Updated=TO_TIMESTAMP('2011-09-04 17:05:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=53563
;
