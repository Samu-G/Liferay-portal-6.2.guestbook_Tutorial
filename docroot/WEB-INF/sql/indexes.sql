create index IX_C437A07C on AB_Entry (groupId, guestbookId);
create index IX_57DD9EFA on AB_Entry (uuid_);
create index IX_110924EE on AB_Entry (uuid_, companyId);
create unique index IX_B6D20EF0 on AB_Entry (uuid_, groupId);

create index IX_D56B15C1 on AB_Guestbook (groupId);
create index IX_601D248B on AB_Guestbook (uuid_);
create index IX_61F32ABD on AB_Guestbook (uuid_, companyId);
create unique index IX_AD13C7F on AB_Guestbook (uuid_, groupId);