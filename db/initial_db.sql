CREATE TABLE public.requests (
    id character varying(300) NOT NULL,
    user_id character varying(300) NOT NULL,
    first_image character varying(300) NOT NULL,
    second_image character varying(300) NOT NULL,
    result boolean ,
    distance numeric ,
    created_at timestamp with time zone NOT NULL
);


ALTER TABLE public.requests OWNER TO "yvmOiJUG";

CREATE TABLE public.users (
    id character varying(300) NOT NULL,
    email character varying(1000) NOT NULL,
    password character varying(3000) NOT NULL,
    name character varying(1000),
    created_at timestamp with time zone NOT NULL
);

ALTER TABLE public.users OWNER TO "yvmOiJUG";

ALTER TABLE ONLY public.users
    ADD CONSTRAINT email_unique_key UNIQUE (email);

ALTER TABLE ONLY public.requests
    ADD CONSTRAINT requests_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.requests
    ADD CONSTRAINT user_fkey FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;