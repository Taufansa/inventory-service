CREATE TABLE public.item (
     id bigserial NOT NULL,
     item_name varchar(255) NOT NULL,
     item_description text NOT NULL,
     item_category varchar(255) NOT NULL,
     item_sku varchar(100) NOT NULL,
     created_at timestamptz DEFAULT now(),
     created_by varchar(50) DEFAULT 'SYSTEM'::character varying,
     CONSTRAINT item_pkey PRIMARY KEY (id)
);

CREATE TABLE public.variant (
    id bigserial NOT NULL,
    item_id bigint NOT NULL,
    variant_name varchar(255) NOT NULL,
    variant_color varchar(100) NOT NULL,
    variant_size varchar(100) NOT NULL,
    variant_weight int NOT NULL,
    created_at timestamptz DEFAULT now(),
    created_by varchar(50) DEFAULT 'SYSTEM'::character varying,
    CONSTRAINT variant_pkey PRIMARY KEY (id)
);

CREATE TABLE public.price (
    id bigserial NOT NULL,
    variant_id bigint NOT NULL,
    price numeric(19, 2) NOT NULL,
    currency varchar(255) NOT NULL,
    created_at timestamptz DEFAULT now(),
    created_by varchar(50) DEFAULT 'SYSTEM'::character varying,
    CONSTRAINT price_pkey PRIMARY KEY (id)
);

CREATE TABLE public.stock (
    id bigserial NOT NULL,
    variant_id bigint NOT NULL,
    available bigint NOT NULL,
    book bigint NOT NULL,
    sold bigint NOT NULL,
    created_at timestamptz DEFAULT now(),
    created_by varchar(50) DEFAULT 'SYSTEM'::character varying,
    CONSTRAINT stock_pkey PRIMARY KEY (id)
);