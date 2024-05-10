DATABASE UPDATE: Thêm table  table lich_su_hoa_don
create table lich_su_hoa_don
(
    id    uniqueidentifier DEFAULT NEWID() primary key,
    id_hoa_don    uniqueidentifier,
    foreign key (id_hoa_don) references hoa_don (id_hoa_don),
	ngay_tao       varchar(20),
    nguoi_tao      varchar(20),
    mo_ta          nvarchar(max),

)
