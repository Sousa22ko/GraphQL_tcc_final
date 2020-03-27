ALTER TABLE ONLY pessoa
    ADD CONSTRAINT fk_pessoa_endereco
    FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco);

-- Definition for index fk_ferias_colaborador (OID = 39252) : 
--
ALTER TABLE ONLY ferias
    ADD CONSTRAINT fk_ferias_pessoa
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa);

--
-- Definition for index fk_parcela_ferias (OID = 39265) : 
--
ALTER TABLE ONLY ferias_parcela
    ADD CONSTRAINT fk_ferias_parcela
    FOREIGN KEY (id_ferias) REFERENCES ferias(id_ferias);

--
-- Definition for index fk_vinculo_cargo (OID = 39299) : 
--
ALTER TABLE ONLY vinculo
    ADD CONSTRAINT fk_vinculo_cargo
    FOREIGN KEY (id_cargo) REFERENCES cargo(id_cargo);
--
-- Definition for index fk_vinculo_setor (OID = 39309) : 
--
ALTER TABLE ONLY vinculo
    ADD CONSTRAINT fk_vinculo_setor
    FOREIGN KEY (id_setor) REFERENCES setor(id_setor);
--
-- Definition for index fk_vinculo_colaborador (OID = 39314) : 
--
ALTER TABLE ONLY vinculo
    ADD CONSTRAINT fk_vinculo_pessoa
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id_pessoa);
--
-- Definition for index fk_setor_pai: 
--
ALTER TABLE ONLY setor
    ADD CONSTRAINT fk_setor_pai
    FOREIGN KEY (id_setor_pai) REFERENCES setor (id_setor);