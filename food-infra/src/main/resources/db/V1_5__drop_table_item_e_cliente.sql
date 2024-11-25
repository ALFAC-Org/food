alter table pedido
drop foreign key pedido_fk1;

alter table item_combo_complemento
drop foreign key item_combo_complemento_fk2;

alter table item_combo
drop foreign key item_combo_fk2;


drop table cliente;
drop table item;
